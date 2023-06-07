import React from 'react'
import Spacer from './Spacer.js'

export default function HeaderComponent({ showLogin }) {
  return (
    <div>
        <header>
            <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                <Spacer axis="horizontal" size={16} />
                <span className="navbar-brand">Your Neighborhood Tool App</span>
                <div>
                  <img src="favicon-wrench-96.png" alt="App Logo" width="85%" />
                </div>
                <button className="btn btn-primary sign-in-btn" onClick={showLogin}>
                  Sign in
                </button>
                <Spacer axis="vertical" size={40} />
            </nav>
        </header>
    </div>
  )
}