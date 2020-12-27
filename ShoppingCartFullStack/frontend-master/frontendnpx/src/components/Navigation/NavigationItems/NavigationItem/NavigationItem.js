import React from 'react';
import styles from './NavigationItem.module.css';
import {NavLink} from 'react-router-dom';

const NavigationItem=(props)=>(
<li className={styles.NavigationItem}>
    <NavLink 
    to={props.link}
    exact ={props.exact}
    activeClassName={styles.active}>{props.children}
    {/* //Here we have given it a activeClassName although it have active as a property
    because css will not be able to identiy it . */}
    </NavLink></li>
    

)
export default NavigationItem;