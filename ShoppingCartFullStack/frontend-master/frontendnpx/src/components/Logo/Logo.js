import React from 'react';
import styles from './Logo.module.css';
import logo from '../../assests/images/logo.png';
const Logo=()=>(
    <div className={styles.Logo}>
        <img src={logo} alt="E-Commerece"></img>
    </div>
)
export default Logo;