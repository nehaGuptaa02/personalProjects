import React from 'react';
import styles from './SideDrawerItem.module.css';
import {NavLink} from 'react-router-dom';

const sideDrawerItem=(props)=>(
<li className={styles.SideDrawerItem}>
    <NavLink 
    to={props.link}
    exact ={props.exact}
    onClick={props.click}
    //  activeClassName={styles.active}
    >{props.children}
    {/* //Here we have given it a activeClassName although it have active as a property
    because css will not be able to identiy it . */}
    </NavLink>
    </li>
    

)
export default sideDrawerItem;