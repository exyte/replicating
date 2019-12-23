//
//  ActivityData.swift
//  ActivityAppReplicating
//
//  Created by Dmitry Shipinev on 06.11.2019.
//  Copyright © 2019 Exyte. All rights reserved.
//

import Foundation

struct ActivityData {
    
    static let weekdays: [(firstLetter: String, movePercentage: Double, exercisePercentage: Double, standPercentage: Double)] = [
        (firstLetter: "M", movePercentage: 0.5, exercisePercentage: 0.8, standPercentage: 0.7),
        (firstLetter: "T", movePercentage: 1.2, exercisePercentage: 1.3, standPercentage: 1.2),
        (firstLetter: "W", movePercentage: 1.9, exercisePercentage: 1.1, standPercentage: 0.25),
        (firstLetter: "T", movePercentage: 0.1, exercisePercentage: 0.95, standPercentage: 0.3),
        (firstLetter: "F", movePercentage: 0.3, exercisePercentage: 0.7, standPercentage: 1.1),
        (firstLetter: "S", movePercentage: 0.8, exercisePercentage: 1.1, standPercentage: 0.8),
        (firstLetter: "S", movePercentage: 1.5, exercisePercentage: 0.4, standPercentage: 1.5)
    ]
    
    static let ringsPercentage: (movePercentage: Double, exercisePercentage: Double, standPercentage: Double) = (movePercentage: 1.1, exercisePercentage: 1.8, standPercentage: 1.5)
    
    static let moveChartData: [Int: Double] = [
        20: Double.random(in: 0...60),
        21: Double.random(in: 0...60),
        22: Double.random(in: 0...60),
        23: Double.random(in: 0...60),
        50: Double.random(in: 0...60),
        51: Double.random(in: 0...60),
        52: Double.random(in: 0...60),
        53: Double.random(in: 0...60),
        54: Double.random(in: 0...60),
        55: Double.random(in: 0...60),
        56: Double.random(in: 0...60),
        57: Double.random(in: 0...60),
        58: Double.random(in: 0...60),
        59: Double.random(in: 0...60),
        60: Double.random(in: 0...60),
        61: Double.random(in: 0...60),
        62: Double.random(in: 0...60),
        63: Double.random(in: 0...60),
        64: Double.random(in: 0...60),
        65: Double.random(in: 0...60),
        90: Double.random(in: 0...60),
        91: Double.random(in: 0...60),
        92: Double.random(in: 0...60),
        93: Double.random(in: 0...60),
        94: Double.random(in: 0...60),
        95: Double.random(in: 0...60)
    ]
    
    static let exerciseChartData: [Int: Double] = [
        30: Double.random(in: 0...60),
        31: Double.random(in: 0...60),
        51: Double.random(in: 0...60),
        52: Double.random(in: 0...60),
        53: Double.random(in: 0...60),
        54: Double.random(in: 0...60),
        55: Double.random(in: 0...60),
        56: Double.random(in: 0...60),
        57: Double.random(in: 0...60),
        80: Double.random(in: 0...60),
        81: Double.random(in: 0...60),
        82: Double.random(in: 0...60),
        83: Double.random(in: 0...60),
        84: Double.random(in: 0...60),
        85: Double.random(in: 0...60),
        86: Double.random(in: 0...60),
        87: Double.random(in: 0...60),
        88: Double.random(in: 0...60)
    ]
    
    static let standChartData: [Int: Bool] = [
        0: true,
        4: true,
        15: true,
        16: true,
        17: true
    ]
    
    static let steps: String = "5 588"
    static let distance: String = "4,5"
    static let flightsClimbed: String = "4"
    
}
