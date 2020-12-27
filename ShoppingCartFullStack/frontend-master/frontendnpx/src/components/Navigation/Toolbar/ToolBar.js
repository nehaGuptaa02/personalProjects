import React, { Component } from 'react';
import NavigationItems from '../NavigationItems/NavigationItems';
import styles from './Toolbar.module.css';
import Logo from '../../Logo/Logo';
import DrawerToggle from '../SideDrawer/DrawerToggle/DrawerToggle';
import SearchIcon from '@material-ui/icons/Search';
import { connect } from 'react-redux';
import * as actionCreators from '../../../store/actions/index';

class ToolBar extends Component {
    constructor(props) {
        super(props)

        this.state = {
            search: ''
        }

        this.onchange = this.onchange.bind(this)
        this.Search = this.Search.bind(this)

    }
    onchange = e => {
        // e.preventDefault();
        this.setState({ search: e.target.value });
    }
    Search = () => {
        console.log('clicked')
        if (this.state.search) {
            console.log('Search clicked')
            this.props.onSearchProduct(this.state.search)
        }
    }

    render() {
        console.log(this.state.search);
        return (

            <header className={styles.Toolbar}>
                <DrawerToggle className={styles.DrawerToggle} clicked={this.props.drawerToggleClicked} />
                <div className={styles.Logo}>
                    <Logo />
                </div>
                <div className={styles.Div}>
                    <input
                        className={styles.SearchBar}
                        label="Search" icon="search" onChange={this.onchange}></input>
                    <SearchIcon onClick={this.Search}
                        className={styles.Icon} />
                </div>
                <nav>
                    <NavigationItems isAuth={this.props.isAuth}
                        email={this.props.email} />
                </nav>
            </header>
        );
    }

}
const mapStateToProps = state => {
    return {
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onSearchProduct: (search) => dispatch(actionCreators.searchProduct(search))
    };
}



export default connect(mapStateToProps, mapDispatchToProps)(ToolBar);

