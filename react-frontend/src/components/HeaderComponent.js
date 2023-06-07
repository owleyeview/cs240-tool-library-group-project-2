import React from 'react'
import Spacer from './Spacer.js'

const HeaderComponent = () => {
  return (
    <div>
        <header>
            <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                <Spacer axis="horizontal" size={16} />
                <div>
                  <img src="favicon-wrench-96.png" alt="App Logo" width="85%" />
                </div>
                <div>
                    <a href="https://www.example.com" className="navbar-brand align-center"><h2>Your Neighbourhood Tool App</h2></a>
                </div>
            </nav>
        </header>
    </div>
  )
}

export default HeaderComponent