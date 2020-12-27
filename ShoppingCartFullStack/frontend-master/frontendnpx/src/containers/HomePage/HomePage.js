import React, { Component } from 'react';
import Aux from '../../hoc/Aux/Aux';
import styles from './HomePage.module.css';
import Toolbar from '../../components/Navigation/Toolbar/ToolBar';
import SideDrawerItems from '../../components/Navigation/SideDrawer/SideDrawerItems';
import { connect } from 'react-redux';
import * as actionCreators from '../../store/actions/index';
class HomePage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showSideDrawer: false
        }
        this.productDetailsHandler = this.productDetailsHandler.bind(this);

    }
    componentDidMount() {
        this.props.onAuthProducts();
        if (this.props.isAuthenticated != null) {
            const token = localStorage.getItem('token');
            this.props.onAuthParentCategories(token);
            this.props.onAuthOrders(token);
        }

    }
    sideDrawerCloseHandler = () => {
        this.setState({ showSideDrawer: false });
    }
    sideDrawerToggleHandler = () => {
        this.setState((prevState) => {
            return { showSideDrawer: !prevState.showSideDrawer }
        });

    }
    productDetailsHandler = (productId, variationId) => {
        this.props.onViewProduct(productId);
        this.props.onViewProductDetails(productId, variationId);
        // this.props.onAuthParentCategories(token);
        if (this.props.authProducts != null) {
            this.props.history.push('/productDetailsPage');

        }
    }
    buttonClicked = () => {
        this.props.history.push('/signIn');
    }
    render() {
        // let homePage = (
        //     <div>
        //         <strong>
        //             <p style={{ textAlign: 'center', fontSize: '35px', color: '#563f46' }}>WELCOME!!!</p>
        //         </strong>
        //         <div className={styles.Div}>

        //             <div className={styles.Images}>
        //                 <img src='https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101563/Product/IMG_20200602_175452_uyidqa.jpg'
        //                     alt='loading' width="395" height="395" />
        //                 <div className={styles.Details}>
        //                     <p><strong>BRAND : </strong>ONLY</p>
        //                     <p><strong>DETAILS : </strong>Women Solid A-Line Dress</p>
        //                     <p><strong>PRICE : </strong>Rs 3499</p>
        //                 </div>
        //             </div>
        //             <div className={styles.Images}>
        //                 <img src='https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101564/Product/IMG_20200602_174751_gk5mfl.jpg'
        //                     alt='loading' width="395" height="395" />
        //                 <div className={styles.Details}>
        //                     <p><strong>BRAND : </strong>Dressberry</p>
        //                     <p><strong>DETAILS : </strong>Women Sneakers</p>
        //                     <p><strong>PRICE : </strong>Rs 1799</p>
        //                 </div>
        //             </div>
        //         </div>
        //         <strong>
        //             <p style={{ textAlign: 'center', fontSize: '25px', color: '#563f46' }}>For details Please SignIn or Signup </p>
        //         </strong>
        //         <button
        //             onClick={this.buttonClicked}
        //             className={styles.Submit}
        //         >Buy Now</button>
        //     </div>
        // );
        // if (this.props.isAuthenticated) {
        
        console.log('authPro', this.props.authProducts);
        let products = this.props.authProducts;
        console.log('search result in homepage', this.props.searchResult)
        if (this.props.searchResult.length != 0) {
            products = this.props.searchResult
            
        }
        console.log('search result in products', products)
        let homePage = (
            <div>
                <h1 style={{ textAlign: 'center' }}>Home Page</h1>
                {

                    products.map(product => (
                        <div
                            onClick={() => this.productDetailsHandler(product.id, product.productVariationDtoSet[0].id)}
                            key={product.id} className={styles.Product}>
                            <div
                            >
                                <img src={product.productVariationDtoSet[0].primaryImageName}
                                    className={styles.ProductImage}
                                    alt="loading..." width="295" height="295"></img>
                            </div>
                            <div className={styles.Details}>
                                <p><strong>NAME : </strong>{product.name}</p>
                                <p><strong>BRAND : </strong>{product.brand}</p>
                                <p><strong>PRICE : </strong>{product.productVariationDtoSet[0].price}</p>
                            </div>
                        </div>

                    ))}
            </div>);
        // }
        return (
            <Aux>
                <Toolbar
                    drawerToggleClicked={this.sideDrawerToggleHandler}
                    isAuth={this.props.isAuthenticated}
                />
                <SideDrawerItems
                    isAuth={this.props.isAuthenticated}
                    open={this.state.showSideDrawer}
                    close={this.sideDrawerCloseHandler}
                />
                <main className={styles.Content}>
                    {homePage}
                </main>
            </Aux>
        );
    }


}
const mapStateToProps = state => {
    return {
        isAuthenticated: state.auth.token !== null,
        authProducts: state.auth.authProducts,
        searchResult: state.product.searchResult
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onAuthProducts: () => dispatch(actionCreators.authProducts()),
        onViewProductDetails: (productId, variationId, token) => dispatch(actionCreators.viewProductDetails(productId, variationId, token)),
        onAuthParentCategories: (token) => dispatch(actionCreators.authParentCategories(token)),
        onAuthOrders: (token) => dispatch(actionCreators.authOrders(token)),
        onViewProduct: (productId) => dispatch(actionCreators.viewProduct(productId))

    };
}
export default connect(mapStateToProps, mapDispatchToProps)(HomePage);