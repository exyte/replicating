//
//  ContentView.swift
//  ActivityAppReplicating
//
//  Created by Dmitry Shipinev on 22.10.2019.
//  Copyright © 2019 Exyte. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    
    var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .top) {
                Color.black.edgesIgnoringSafeArea(.all)
                ScrollView {
                    VStack {
                        Spacer(minLength: Constants.navigationBarHeight).frame(width: geometry.size.width, height: Constants.navigationBarHeight, alignment: .top)
                        self.createRings()
                        self.createCharts()
                        self.createFooter()
                    }
                }
                self.createNavigationBar(geometry.size)
            }
        }
    }
    
    func createRings() -> some View {
        ZStack {
            RingView(
                percentage: ActivityData.ringsPercentage.standPercentage,
                backgroundColor: Color.standRingBackground,
                startColor: Color.standRingStartColor,
                endColor: Color.standRingEndColor,
                thickness: Constants.mainRingThickness
            )
                .frame(width: 150, height: 150)
                .aspectRatio(contentMode: .fit)
            RingView(
                percentage: ActivityData.ringsPercentage.exercisePercentage,
                backgroundColor: Color.exerciseRingBackground,
                startColor: Color.exerciseRingStartColor,
                endColor: Color.exerciseRingEndColor,
                thickness: Constants.mainRingThickness
            )
                .frame(width: 215, height: 215)
                .aspectRatio(contentMode: .fit)
            RingView(
                percentage: ActivityData.ringsPercentage.movePercentage,
                backgroundColor: Color.moveRingBackground,
                startColor: Color.moveRingStartColor,
                endColor: Color.moveRingEndColor,
                thickness: Constants.mainRingThickness
            )
                .frame(width: 280, height: 280)
                .aspectRatio(contentMode: .fit)
        }
    }
    
    func createCharts() -> some View {
        Group {
            // Move Chart
            BarChartView(
                title: "Move",
                progress: "330",
                goal: "300",
                total: "2 175 CAL",
                average: "56",
                unit: "CAL",
                data: ActivityData.moveChartData,
                textColor: Color.moveTextColor,
                barStartColor: Color.moveBarStartColor,
                barEndColor: Color.moveBarEndColor
            )
                .padding([.bottom], 25)
            
            // Exercise Chart
            BarChartView(
                title: "Exercise",
                progress: "22",
                goal: "30",
                total: "8H 42M",
                average: "11",
                unit: "MIN",
                data: ActivityData.exerciseChartData,
                textColor: Color.exerciseTextColor,
                barStartColor: Color.exerciseBarStartColor,
                barEndColor: Color.exerciseBarEndColor
            )
                .padding([.bottom], 25)
            
            // Stand Chart
            StandChartView(
                title: "Stand",
                progress: "10",
                goal: "12",
                idle: "0",
                unit: "HRS",
                data: ActivityData.standChartData,
                textColor: Color.standTextColor,
                barStartColor: Color.standBarStartColor,
                barEndColor: Color.standBarEndColor
            )
                .padding([.bottom], 25)
        }
    }
    
    func createFooter() -> some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack(spacing: 60) {
                VStack(alignment: .leading, spacing: 0) {
                    Text("Steps")
                        .font(Font.system(size: 18, weight: .regular, design: .default))
                        .kerning(0.05)
                        .foregroundColor(Color.white)
                    Text("5 588")
                        .font(Font.system(size: 28, weight: .semibold, design: .rounded))
                        .kerning(0.25)
                        .foregroundColor(Color.activityValueText)
                        .padding([.top], -3)
                }
                VStack(alignment: .leading, spacing: 0) {
                    Text("Distance").font(Font.system(size: 18, weight: .regular, design: .default)).kerning(0.05).foregroundColor(Color.white)
                    (
                        Text("4,5")
                            .font(Font.system(size: 28, weight: .semibold, design: .rounded))
                            .kerning(0.25)
                            .foregroundColor(Color.activityValueText)
                        + Text("KM")
                            .font(Font.system(size: 24, weight: .semibold, design: .rounded))
                            .kerning(0.3).foregroundColor(Color.activityValueText)
                    )
                        .padding([.top], -3)
                }
                Spacer()
            }
            Divider()
                .background(Color.dividerBackground)
                .frame(height: 2)
                .padding(EdgeInsets(top: 10, leading: 0, bottom: 15, trailing: 0))
            Text("Flights Climbed")
                .font(Font.system(size: 18, weight: .regular, design: .default))
                .kerning(0.05)
                .foregroundColor(Color.white)
            Text("4")
                .font(Font.system(size: 28, weight: .semibold, design: .rounded))
                .kerning(0.25)
                .foregroundColor(Color.activityValueText)
                .padding([.top], -3)
            }
            .padding([.leading], 15)
    }
    
    func createNavigationBar(_ geometrySize: CGSize) -> some View {
        ZStack(alignment: .top) {
            BlurView(style: .dark).edgesIgnoringSafeArea(.top)
            VStack {
                HStack {
                    ForEach(0..<ActivityData.weekdays.count) { item in
                        VStack(spacing: 5) {
                            Text("\(ActivityData.weekdays[item].firstLetter)")
                                .font(Font.system(size: 10, weight: .regular, design: .default))
                                .foregroundColor(Color.white)
                            ZStack {
                                RingView(
                                    percentage: ActivityData.weekdays[item].standPercentage,
                                    backgroundColor: Color.standRingWeekdayBackground,
                                    startColor: Color.standRingStartColor,
                                    endColor: Color.standRingEndColor,
                                    thickness: Constants.weekdayRingThickness
                                )
                                    .frame(width: 20, height: 20)
                                    .aspectRatio(contentMode: .fit)
                                RingView(
                                    percentage: ActivityData.weekdays[item].exercisePercentage,
                                    backgroundColor: Color.exerciseRingWeekdayBackground,
                                    startColor: Color.exerciseRingStartColor,
                                    endColor: Color.exerciseRingEndColor,
                                    thickness: Constants.weekdayRingThickness
                                )
                                    .frame(width: 30, height: 30)
                                    .aspectRatio(contentMode: .fit)
                                RingView(
                                    percentage: ActivityData.weekdays[item].movePercentage,
                                    backgroundColor: Color.moveRingWeekdayBackground,
                                    startColor: Color.moveRingStartColor,
                                    endColor: Color.moveRingEndColor,
                                    thickness: Constants.weekdayRingThickness
                                )
                                    .frame(width: 40, height: 40)
                                    .aspectRatio(contentMode: .fit)
                            }
                        }
                    }
                }
            }
        }
            .frame(width: geometrySize.width, height: Constants.navigationBarHeight, alignment: .top)
    }
    
}

struct ContentView_Previews: PreviewProvider {
    
    static var previews: some View {
        ContentView()
    }
    
}
