import React, { Component } from 'react';
import styles from './SideDrawerItems.module.css';
import Backdrop from '../../UI/Backdrop/Backdrop';
import Aux from '../../../hoc/Aux/Aux';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import SideDrawerItem from './SideDrawerItem/SideDrawerItem';
import { connect } from 'react-redux';
import { withRouter } from "react-router-dom";
import * as actionCreators from '../../../store/actions/index';
var headerStyle = {
    color: '#563f46'
};
class SideDrawer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            show: false,
            catId: null
        }
        this.openAllCategoriesHandler = this.openAllCategoriesHandler.bind(this);
        this.yourAccountHandler = this.yourAccountHandler.bind(this);
        this.sellerAccountHandler = this.sellerAccountHandler.bind(this);
        this.viewProductsHandler = this.viewProductsHandler.bind(this);

    }
    componentDidMount() {
        const token = localStorage.getItem('token');
        this.props.onAuthParentCategories(token);
    }
    openAllCategoriesHandler = (catId) => {
        this.setState({ show: !this.state.show })
        this.setState({ catId: catId });
        const token = localStorage.getItem('token');
        this.props.onViewCategory(catId, token);
    }
    yourAccountHandler = () => {
        console.log('side drawer clicked')
        const token = localStorage.getItem('token');
        this.props.onViewCustomerProfile(token);
        this.props.history.replace('/viewCustomerProfile');

    }
    sellerAccountHandler = () => {
        console.log('side drawer clicked')
        const token = localStorage.getItem('token');
        this.props.onViewSellerProfile(token);
    }
    viewProductsHandler = (e, subCategoryId) => {
        console.log('clicked')
        const token = localStorage.getItem('token');
        this.props.onViewAllProducts(subCategoryId, token);
        // if (this.props.products != null) {
        //     this.props.history.push('/productsPage');
        // }
        if (this.props.products != null) {
            e.preventDefault()
            this.props.history.push({
                pathname: '/productsPage',
                state: {
                    subCategoryId: subCategoryId
                }
            })
        }

    }
    render() {
        let type = localStorage.getItem('type');
        let firstName = localStorage.getItem('firstName');
        let attatchedClasses = [styles.SideDrawer, styles.Close];
        if (this.props.open) {
            attatchedClasses = [styles.SideDrawer, styles.Open]
        }
        // let category = (
        //     <p style={{ color: '#563f46', fontSize: '20px' }}>Please log in as a customer to view
        // all categories and product assosiated with them </p>
        // );
        let category = "";
        if (type === 'customer') {
            category = (
                <div className={styles.Div}>
                    <h4 style={headerStyle}>SHOP BY CATEGORY</h4>
                    {this.props.parentCategories.map(cat => (
                        <div key={cat.id} className={styles.ParentCategory}>
                            <strong><a key={cat.id}
                                onClick={() => this.openAllCategoriesHandler(cat.id)}
                            >{cat.categoryName}</a></strong>
                            <br></br>
                            {this.state.show && this.state.catId == cat.id ?
                                <div>
                                    {this.props.subCategories.map(subCategory => (
                                        <div
                                            className={styles.Child}
                                            onClick={(e) => this.viewProductsHandler(e, subCategory.id)}
                                            key={subCategory.id} className={styles.Div}>
                                            <a > {subCategory.name}</a>
                                        </div>
                                    ))
                                    }
                                </div> : null}
                        </div>
                    ))}

                </div>
            );
        }
        return (
            <Aux>
                <Backdrop show={this.props.open}
                    clicked={this.props.close} />
                {/* //Whenever we will click anyywhere in the side drawer it will be closed */}
                <div className={attatchedClasses.join(' ')} >
                    <header className={styles.Header}>
                        <AccountCircleIcon style={{ fontSize: 60 }} />
           Hello {this.props.isAuth ? firstName : "SignIn"}
                    </header>
                    <nav>
                        {category}
                        <hr></hr>
                        <div className={styles.Div}>
                            <h4 style={headerStyle}>HELP AND SETTINGS</h4>
                            {this.props.isAuth && type === "customer" ? <SideDrawerItem link="/viewCustomerProfile" click={this.yourAccountHandler} >Your Account</SideDrawerItem> : null}
                            {this.props.isAuth && type === "seller" ? <SideDrawerItem link="/viewSellerProfile" click={this.sellerAccountHandler} >Your Account</SideDrawerItem> : null}
                            {this.props.isAuth && type === "admin" ? <SideDrawerItem link="/admin" >Your Account</SideDrawerItem> : null}
                            {this.props.isAuth && type === "admin" ? <SideDrawerItem link="/adminCategory" >Admin Category</SideDrawerItem> : null}
                            {this.props.isAuth && type === "seller" ? <SideDrawerItem link="/viewAllCategories" >Seller Category</SideDrawerItem> : null}
                            {this.props.isAuth && type === "seller" ? <SideDrawerItem link="/viewAllProductsBySeller" >Seller Product</SideDrawerItem> : null}
                            <SideDrawerItem link="/" >Home Page</SideDrawerItem>
                            {this.props.isAuth ? <SideDrawerItem link="/signOut" >Sign Out</SideDrawerItem>
                                : <SideDrawerItem link="/signIn" >Sign In</SideDrawerItem>}
                        </div>
                        <div>
                        </div>
                    </nav>
                </div>
            </Aux>
        );
    }


}
const mapStateToProps = state => {
    return {
        subCategories: state.category.subCategories,
        parentCategories: state.auth.parentCategories,
        products: state.product.products

    };
}
const mapDispatchToProps = dispatch => {
    return {
        onViewCategory: (id, token) => dispatch(actionCreators.viewCategory(id, token)),
        onViewCustomerProfile: (token) => dispatch(actionCreators.viewCustomerProfile(token)),
        onViewSellerProfile: (token) => dispatch(actionCreators.viewSellerProfile(token)),
        onAuthParentCategories: (token) => dispatch(actionCreators.authParentCategories(token)),
        onViewAllProducts: (subCategoryId, token) => dispatch(actionCreators.viewAllProducts(subCategoryId, token)),

    };
}
export default withRouter(connect(mapStateToProps, mapDispatchToProps)(SideDrawer));