import React, { Component } from 'react';
import Aux from '../Aux/Aux';
import styles from './Layout.module.css';
import Toolbar from '../../components/Navigation/Toolbar/ToolBar';
import StickyFooter from '../../components/UI/StickyFooter/StickyFooter';
import SideDrawerItems from '../../components/Navigation/SideDrawer/SideDrawerItems';
import { connect } from 'react-redux';
import ErrorBoundary from '../../components/Error/ErrorBoundary';
class Layout extends Component {
    state = {
        showSideDrawer: false
    }
    sideDrawerCloseHandler = () => {
        this.setState({ showSideDrawer: false });
    }
    sideDrawerToggleHandler = () => {
        this.setState((prevState) => {
            return { showSideDrawer: !prevState.showSideDrawer }
        });

    }
    render() {
        return (
            // <ErrorBoundary>
                <Aux>
                    <Toolbar
                        drawerToggleClicked={this.sideDrawerToggleHandler}
                        isAuth={this.props.isAuthenticated}
                        email={this.props.email} />
                    <SideDrawerItems
                        isAuth={this.props.isAuthenticated}
                        open={this.state.showSideDrawer}
                        close={this.sideDrawerCloseHandler}
                    />
                    <main className={styles.Content}>
                        {this.props.children}
                    </main>
                    <StickyFooter />
                </Aux>
            // </ErrorBoundary>
        );
    }

}
const mapStateToProps = state => {
    return {
        isAuthenticated: state.auth.token !== null,
        email: state.auth.email
    };
}
export default connect(mapStateToProps)(Layout);