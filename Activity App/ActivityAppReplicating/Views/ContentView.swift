//
//  ContentView.swift
//  ActivityAppReplicating
//
//  Created by Dmitry Shipinev on 22.10.2019.
//  Copyright © 2019 Exyte. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    
    enum WeekDays: Int {
        case Monday, Tuesday, Wednsday, Thursday, Friday, Satuday, Sunday
    }
    
    @State private var weekDay: Int = WeekDays.Monday.rawValue
    
    var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .top) {
                Color.black.edgesIgnoringSafeArea(.all)
                ScrollView {
                    VStack {
                        Spacer(minLength: Constants.navigationBarHeight).frame(width: geometry.size.width, height: Constants.navigationBarHeight, alignment: .top)
                        
                        // showing the rings of main ring chart in dependance of selected weekday
                               switch weekDay {
                               case 0:
                                   createRings(
                                       standPercentage: ActivityData.weekdays[0].standPercentage,   standBackgroundColor: .standRingWeekdayBackground,
                                       standStartColor: .standRingStartColor,
                                       standEndColor: .standRingEndColor,
                                       
                                       exePercentage: ActivityData.weekdays[0].exercisePercentage,
                                       exeBackgroundColor: .exerciseRingWeekdayBackground,
                                       exeStartColor: .exerciseRingStartColor,
                                       exeEndColor: .exerciseRingEndColor,
                                       
                                       movePercentage: ActivityData.weekdays[0].movePercentage,
                                       moveBackgroundColor: .moveRingWeekdayBackground,
                                       moveStartColor: .moveRingStartColor,
                                       moveEndColor: .moveRingEndColor,
                                       thickness: Constants.mainRingThickness
                                   )
                               case 1:
                                   createRings(
                                       standPercentage: ActivityData.weekdays[1].standPercentage,   standBackgroundColor: .standRingWeekdayBackground,
                                       standStartColor: .standRingStartColor,
                                       standEndColor: .standRingEndColor,
                                       
                                       exePercentage: ActivityData.weekdays[1].exercisePercentage,
                                       exeBackgroundColor: .exerciseRingWeekdayBackground,
                                       exeStartColor: .exerciseRingStartColor,
                                       exeEndColor: .exerciseRingEndColor,
                                       
                                       movePercentage: ActivityData.weekdays[1].movePercentage,
                                       moveBackgroundColor: .moveRingWeekdayBackground,
                                       moveStartColor: .moveRingStartColor,
                                       moveEndColor: .moveRingEndColor,
                                       thickness: Constants.mainRingThickness
                                   )
                               case 2:
                                   createRings(
                                       standPercentage: ActivityData.weekdays[2].standPercentage,   standBackgroundColor: .standRingWeekdayBackground,
                                       standStartColor: .standRingStartColor,
                                       standEndColor: .standRingEndColor,
                                       
                                       exePercentage: ActivityData.weekdays[2].exercisePercentage,
                                       exeBackgroundColor: .exerciseRingWeekdayBackground,
                                       exeStartColor: .exerciseRingStartColor,
                                       exeEndColor: .exerciseRingEndColor,
                                       
                                       movePercentage: ActivityData.weekdays[2].movePercentage,
                                       moveBackgroundColor: .moveRingWeekdayBackground,
                                       moveStartColor: .moveRingStartColor,
                                       moveEndColor: .moveRingEndColor,
                                       thickness: Constants.mainRingThickness
                                   )
                               case 3:
                                   createRings(
                                       standPercentage: ActivityData.weekdays[3].standPercentage,   standBackgroundColor: .standRingWeekdayBackground,
                                       standStartColor: .standRingStartColor,
                                       standEndColor: .standRingEndColor,
                                       
                                       exePercentage: ActivityData.weekdays[3].exercisePercentage,
                                       exeBackgroundColor: .exerciseRingWeekdayBackground,
                                       exeStartColor: .exerciseRingStartColor,
                                       exeEndColor: .exerciseRingEndColor,
                                       
                                       movePercentage: ActivityData.weekdays[3].movePercentage,
                                       moveBackgroundColor: .moveRingWeekdayBackground,
                                       moveStartColor: .moveRingStartColor,
                                       moveEndColor: .moveRingEndColor,
                                       thickness: Constants.mainRingThickness
                                   )
                               case 4:
                                   createRings(
                                       standPercentage: ActivityData.weekdays[4].standPercentage,   standBackgroundColor: .standRingWeekdayBackground,
                                       standStartColor: .standRingStartColor,
                                       standEndColor: .standRingEndColor,
                                       
                                       exePercentage: ActivityData.weekdays[4].exercisePercentage,
                                       exeBackgroundColor: .exerciseRingWeekdayBackground,
                                       exeStartColor: .exerciseRingStartColor,
                                       exeEndColor: .exerciseRingEndColor,
                                       
                                       movePercentage: ActivityData.weekdays[4].movePercentage,
                                       moveBackgroundColor: .moveRingWeekdayBackground,
                                       moveStartColor: .moveRingStartColor,
                                       moveEndColor: .moveRingEndColor,
                                       thickness: Constants.mainRingThickness
                                   )
                               case 5:
                                   createRings(
                                       standPercentage: ActivityData.weekdays[5].standPercentage,   standBackgroundColor: .standRingWeekdayBackground,
                                       standStartColor: .standRingStartColor,
                                       standEndColor: .standRingEndColor,
                                       
                                       exePercentage: ActivityData.weekdays[5].exercisePercentage,
                                       exeBackgroundColor: .exerciseRingWeekdayBackground,
                                       exeStartColor: .exerciseRingStartColor,
                                       exeEndColor: .exerciseRingEndColor,
                                       
                                       movePercentage: ActivityData.weekdays[5].movePercentage,
                                       moveBackgroundColor: .moveRingWeekdayBackground,
                                       moveStartColor: .moveRingStartColor,
                                       moveEndColor: .moveRingEndColor,
                                       thickness: Constants.mainRingThickness
                                   )
                               case 6:
                                   createRings(
                                       standPercentage: ActivityData.weekdays[6].standPercentage,   standBackgroundColor: .standRingWeekdayBackground,
                                       standStartColor: .standRingStartColor,
                                       standEndColor: .standRingEndColor,
                                       
                                       exePercentage: ActivityData.weekdays[6].exercisePercentage,
                                       exeBackgroundColor: .exerciseRingWeekdayBackground,
                                       exeStartColor: .exerciseRingStartColor,
                                       exeEndColor: .exerciseRingEndColor,
                                       
                                       movePercentage: ActivityData.weekdays[6].movePercentage,
                                       moveBackgroundColor: .moveRingWeekdayBackground,
                                       moveStartColor: .moveRingStartColor,
                                       moveEndColor: .moveRingEndColor,
                                       thickness: Constants.mainRingThickness
                                   )
                               default:
                                   createRings()
                               }
                        
                        
                        self.createCharts()
                        self.createFooter()
                    }
                }
                self.createNavigationBar(geometry.size)
            }
        }
    }
    
    //  function of rings creation with default parameters
    func createRings(
        standPercentage: Double = ActivityData.ringsPercentage.standPercentage,
        standBackgroundColor: Color = .standRingBackground,
        standStartColor: Color = .standRingStartColor,
        standEndColor: Color = .standRingEndColor,
        
        exePercentage: Double = ActivityData.ringsPercentage.exercisePercentage,
        exeBackgroundColor: Color = .exerciseRingBackground,
        exeStartColor: Color = .exerciseRingStartColor,
        exeEndColor: Color = .exerciseRingEndColor,
        
        movePercentage: Double = ActivityData.ringsPercentage.movePercentage,
        moveBackgroundColor: Color = .moveRingBackground,
        moveStartColor: Color = .moveRingStartColor,
        moveEndColor: Color = .moveRingEndColor,
        
        thickness: CGFloat = Constants.mainRingThickness
        ) -> some View {
        ZStack {
            RingView(
                percentage: standPercentage,
                backgroundColor: standBackgroundColor,
                startColor: standStartColor,
                endColor: standEndColor,
                thickness: thickness
            )
            .frame(width: 150, height: 150)
            .aspectRatio(contentMode: .fit)
            
            RingView(
                percentage: exePercentage,
                backgroundColor: exeBackgroundColor,
                startColor: exeStartColor,
                endColor: exeEndColor,
                thickness: thickness
            )
            .frame(width: 215, height: 215)
            .aspectRatio(contentMode: .fit)
            
            RingView(
                percentage: movePercentage,
                backgroundColor: moveBackgroundColor,
                startColor: moveStartColor,
                endColor: moveEndColor,
                thickness: thickness
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
                            // binding of weekDay property to selection of particulary day in navigation bar
                            .onTapGesture {
                                weekDay = item
                            }
                            // making ring diagram of  weekday blured once selected
                            .blur(radius: weekDay == item ? 2 : 0)
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
