//
//  HostingController.swift
//  ActivityAppReplicating
//
//  Created by Dmitry Shipinev on 06.11.2019.
//  Copyright © 2019 Exyte. All rights reserved.
//

import Foundation
import UIKit
import SwiftUI

class HostingController: UIHostingController<ContentView> {
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }
    
}
