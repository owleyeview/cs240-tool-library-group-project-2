import React from 'react'
import Spacer from './Spacer.js'

export default function HeaderComponent({ showLogin }) {
  return (
    <div>
        <header>
            <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                <Spacer axis="horizontal" size={16} />
                <div>
                  <img src="favicon-wrench-96.png" alt="App Logo" width="85%" />
                </div>
                <span className="navbar-brand"><h2>Your Neighborhood Tool App</h2></span>
                <button className="btn btn-primary sign-in-btn" onClick={showLogin}>
                  Sign in
                </button>
                <Spacer axis="horizontal" size={16}></Spacer>
                <Spacer axis="vertical" size={40} />
            </nav>
        </header>
    </div>
  )
}