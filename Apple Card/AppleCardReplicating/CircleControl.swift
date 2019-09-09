//
//  CircleControl.swift
//  AppleCardReplicating
//
//  Created by Daniil Manin on 8/28/19.
//  Copyright © 2019 Exyte. All rights reserved.
//

import SwiftUI
import UIKit

struct CircleControl: View {
    
    let totalBalance: Double
    let segments: [Segment]
    
    @Binding var selectedSegment: Segment?
    @State private var currentValue: Double = 250.0
    
    private let lineWidth: CGFloat = 44.0
    
    var body: some View {
        let body = GeometryReader { geometry in
            self.createBody(size: geometry.size)
        }
            
        return body.onAppear {
            self.selectedSegment = self.currentSegment()
        }
    }
    
    // MARK: - Private
    
    private func createBody(size: CGSize) -> some View {
        let controlRadius = size.width / 2.0
        
        return ZStack {
            createOuterCircle(radius: controlRadius)
            createInnerCircle(radius: controlRadius)
            createProgressArc(radius: controlRadius)
            createTopArc(radius: controlRadius)
            createPoints(radius: controlRadius)
            createControlPoint(radius: controlRadius)
            createCurrentValueText(radius: controlRadius)
        }
    }
    
    private func createInnerCircle(radius: CGFloat) -> some View {
        let diametr = radius * 2.0 - self.lineWidth * 2.0
        let innerDiametr = diametr - 4.0
        return Group {
            Circle()
                .fill(Color.white)
                .frame(width: diametr, height: diametr)
            
            Circle()
                .fill(Color.background)
                .frame(width: innerDiametr, height: innerDiametr)
        }
    }
    
    private func createOuterCircle(radius: CGFloat) -> some View {
        let diametr = radius * 2.0 - 4.0
        return Group {
            Circle().fill(Color.white)
            Circle().fill(Color.controlFill)
                .frame(width: diametr, height: diametr)
        }
    }
    
    private func createProgressArc(radius: CGFloat) -> some View {
        let center = CGPoint(x: radius, y: radius)
        let angle = Double(currentValue / totalBalance * 2 * .pi - .pi / 2)
        let color = currentSegment()?.color ?? Color.white
        let gradient = LinearGradient(
            gradient: Gradient(colors: [color.opacity(0.6), color]),
            startPoint: .leading,
            endPoint: .trailing)
        
        return Path { path in
            path.addArc(
                center: center,
                radius: radius - lineWidth / 2.0 - 1.0,
                startAngle: .radians(-.pi / 2.0),
                endAngle: .radians(angle),
                clockwise: false)
            path = path.strokedPath(.init(lineWidth: lineWidth - 2.0))
        }.fill(gradient)
    }
    
    private func createTopArc(radius: CGFloat) -> some View {
        let center = CGPoint(x: radius, y: lineWidth / 2.0 + 1.0)
        let rect = CGRect(
            x: radius - lineWidth / 2.0,
            y: 2.0,
            width: lineWidth,
            height: lineWidth - 2.0)
        
        return Group {
            Path { path in
                path.addEllipse(in: rect)
            }.foregroundColor(Color.controlFill)
            
            Path { path in
                path.addArc(
                    center: center,
                    radius: lineWidth / 2.0,
                    startAngle: .radians(-.pi / 2.0),
                    endAngle: .radians(.pi / 2.0),
                    clockwise: false)
                path = path.strokedPath(.init(lineWidth: 2.0))
            }.foregroundColor(.white)
        }
    }
    
    private func createPoints(radius: CGFloat) -> some View {
        return Group {
            ForEach(0..<segments.count) { index in
                self.createPoint(valuePosition: self.segments[index].amount, radius: radius)
            }
            createPoint(valuePosition: totalBalance, radius: radius)
        }
    }
    
    private func createPoint(valuePosition: Double, radius: CGFloat) -> some View {
        let pointWidth: CGFloat = 16.0
        let angle = CGFloat(valuePosition / totalBalance * 2 * .pi - .pi / 2)
        let center = CGPoint(x: radius, y: radius)
        let radius = radius - lineWidth / 2.0 - 1.0
        let x = center.x + radius * cos(angle)
        let y = center.y + radius * sin(angle)
        let pointCenter = CGPoint(x: x, y: y)
        let rect = CGRect(
            x: pointCenter.x - pointWidth / 2.0,
            y: pointCenter.y - pointWidth / 2.0,
            width: pointWidth,
            height: pointWidth)
      
        return Path { path in
                path.addEllipse(in: rect)
            }.foregroundColor(.point)
    }
    
    private func createCurrentValueText(radius: CGFloat) -> some View {
        let diametr = radius * 2.0 - lineWidth * 2.0 - 16.0
        return Group {
            Text("$")
                .font(Font.system(size: 30.0, weight: .bold, design: .rounded))
                .baselineOffset(15.0)
                +
            Text("\(String(format: "%.2f", Double(currentValue)))")
                .font(Font.system(size: 50.0, weight: .bold, design: .rounded))
        }
        .frame(width: diametr, height: 75.0, alignment: .center)
        .minimumScaleFactor(0.5)
    }
    
    private func createControlPoint(radius: CGFloat) -> some View {
        let angle = CGFloat(currentValue / totalBalance * 2 * .pi - .pi / 2.0)
        let controlRadius = radius - lineWidth / 2.0 - 1.0
        let center = CGPoint(x: radius, y: radius)
        let x = center.x + controlRadius * cos(angle)
        let y = center.y + controlRadius * sin(angle)
        let pointCenter = CGPoint(x: x, y: y)
        
        let borderRect = CGRect(
            x: pointCenter.x - lineWidth / 2.0,
            y: pointCenter.y - lineWidth / 2.0,
            width: lineWidth + 2.0,
            height: lineWidth + 2.0)
        
        let rect = CGRect(
            x: pointCenter.x - lineWidth / 2.0 + 4.0,
            y: pointCenter.y - lineWidth / 2.0 + 4.0,
            width: lineWidth - 6.0,
            height: lineWidth - 6.0)
        
        let color = currentSegment()?.color ?? .white
        
        let dragGesture = DragGesture(minimumDistance: 10.0).onChanged { value in
            self.change(value: value, radius: radius)
        }
        
        return Group {
            Path { path in
                path.addEllipse(in: borderRect)
            }.foregroundColor(.white)
            
            Path { path in
                path.addEllipse(in: rect)
            }.foregroundColor(color)
        }.gesture(dragGesture)
    }

    
    private func change(value: DragGesture.Value, radius: CGFloat) {
        let vector = CGVector(dx: value.location.x - radius, dy: value.location.y - radius)
        let angle = atan2(vector.dy, vector.dx) + .pi / 2.0
        let fixedAngle = angle < 0.0 ? angle + 2.0 * .pi : angle
        let currentValue = Double(fixedAngle) / (2.0 * .pi) * totalBalance
        
        if let segment = nearestSegmentPoint(value: currentValue) {
            self.currentValue = segment.amount
            selectedSegment = segment
        } else {
            self.currentValue = currentValue.rounded(.toNearestOrAwayFromZero)
            if self.currentValue > totalBalance - 50.0 {
                self.currentValue = totalBalance
            }
        }
        
        self.currentValue = max(0, self.currentValue)
    }
    
    // MARK: -
    
    private func currentSegment() -> Segment? {
        return segments.last { $0.amount <= currentValue } ?? segments.first
    }
    
    private func nearestSegmentPoint(value: Double) -> Segment? {
        let magnetCoef = min(totalBalance * 0.025, 50.0)
        return segments.first { $0.amount < value + magnetCoef && $0.amount > value - magnetCoef }
    }
}

struct Segment: Equatable {
    
    let color: Color
    let amount: Double
    let title: String
    let description: String
}
