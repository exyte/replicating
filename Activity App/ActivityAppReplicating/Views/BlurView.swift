//
//  BlurView.swift
//  ActivityAppReplicating
//
//  Created by Dmitry Shipinev on 06.11.2019.
//  Copyright © 2019 Exyte. All rights reserved.
//

import SwiftUI

struct BlurView: UIViewRepresentable {

    let style: UIBlurEffect.Style

    func makeUIView(context: UIViewRepresentableContext<BlurView>) -> UIView {
        let containerView = UIView()
        containerView.backgroundColor = .clear
        let blurEffect = UIBlurEffect(style: style)
        let blurView = UIVisualEffectView(effect: blurEffect)
        blurView.translatesAutoresizingMaskIntoConstraints = false
        containerView.insertSubview(blurView, at: 0)
        blurView.heightAnchor.constraint(equalTo: containerView.heightAnchor).isActive = true
        blurView.widthAnchor.constraint(equalTo: containerView.widthAnchor).isActive = true
        return containerView
    }

    func updateUIView(_ uiView: UIView, context: UIViewRepresentableContext<BlurView>) {}

}
