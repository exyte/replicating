//
//  StandChartView.swift
//  ActivityAppReplicating
//
//  Created by Dmitry Shipinev on 06.11.2019.
//  Copyright © 2019 Exyte. All rights reserved.
//

import SwiftUI

struct StandChartView: View {
    
    var title: String
    var progress: String
    var goal: String
    var idle: String
    var unit: String
    var data: [Int: Bool]
    var textColor: Color
    var barStartColor: Color
    var barEndColor: Color
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            VStack(alignment: .leading, spacing: 0) {
                Text(title)
                    .font(Font.system(size: 18, weight: .regular, design: .default))
                    .kerning(0.05)
                    .foregroundColor(Color.white)
                    (
                        Text("\(progress)/\(goal)")
                            .font(Font.system(size: 28, weight: .semibold, design: .rounded))
                            .kerning(0.25)
                            .foregroundColor(textColor)
                        + Text(unit)
                            .font(Font.system(size: 24, weight: .semibold, design: .rounded))
                            .kerning(0.3)
                            .foregroundColor(textColor)
                    )
                    .padding([.top], -3)
            }
                .padding([.bottom], 10)
            self.createStandChart()
                .padding([.bottom], 2)
            self.createLegend()
                .frame(width: 341, height: 14)
            Text("\(idle) IDLE HOURS")
                .font(Font.system(size: 12, weight: .regular, design: .default))
                .kerning(0.3)
                .foregroundColor(textColor)
        }
    }
    
    func createStandChart() -> some View {
        let maxColumnIndex = Constants.standChartColumns - 1
        let maxGridColumnIndex = maxColumnIndex - 3
        return ZStack(alignment: .bottom) {
            HStack(alignment: .center, spacing: 13.8) {
                VStack(alignment: .center, spacing: Constants.standChartGridColumnSpacing) {
                    ForEach(0..<maxGridColumnIndex) { i in
                        Rectangle()
                            .fill(Color(red: 129 / 255, green: 129 / 255, blue: 129 / 255))
                            .frame(width: 1, height: 1)
                    }
                }
                ForEach(0..<maxColumnIndex - 1) { i in
                    VStack(alignment: .center, spacing: Constants.standChartGridColumnSpacing) {
                        ForEach(0..<maxColumnIndex - 3) { i in
                            Rectangle()
                                .fill(Color(red: 71 / 255, green: 72 / 255, blue: 73 / 255))
                                .frame(width: 1, height: 1)
                        }
                    }
                }
                VStack(alignment: .center, spacing: Constants.standChartGridColumnSpacing) {
                    ForEach(0..<maxGridColumnIndex) { i in
                        Rectangle()
                            .fill(Color(red: 129 / 255, green: 129 / 255, blue: 129 / 255))
                            .frame(width: 1, height: 1)
                    }
                }
            }
            HStack(alignment: .center, spacing: Constants.standChartColumnSpacing) {
                ForEach(0..<maxColumnIndex) { i in
                    Rectangle()
                        .fill(LinearGradient(gradient: Gradient(colors: [self.barStartColor, self.barEndColor]), startPoint: .bottom, endPoint: .top))
                        .frame(width: Constants.standChartColumnWidth, height: (self.data[i] ?? false) ? CGFloat(maxGridColumnIndex - 1) * (Constants.standChartGridColumnSpacing + 1) + 1 : 0)
                        .cornerRadius(Constants.standChartColumnWidth / 2)
                }
            }
            .drawingGroup()
        }
    }
    
    func createLegend() -> some View {
       return HStack(alignment: .center) {
            self.createLegendItem("00:00")
            Spacer()
            self.createLegendItem("06:00")
            Spacer()
            self.createLegendItem("12:00")
            Spacer()
            self.createLegendItem("18:00")
        }
    }
    
    func createLegendItem(_ time: String) -> some View {
        return Text(time)
            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
            .font(Font.system(size: 11, weight: .medium, design: .default))
            .foregroundColor(Color.chartLegendText)
    }

}

