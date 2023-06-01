import React from 'react'
import Spacer from './Spacer.js'

const HeaderComponent = () => {
  return (
    <div>
        <header>
            <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                <Spacer axis="horizontal" size={16} />
                <div>
                    <a href="https://www.example.com" className="navbar-brand">Your Neighbourhood Tool App</a>
                </div>
                <Spacer axis="vertical" size={40} />
            </nav>
        </header>
    </div>
  )
}

export default HeaderComponent