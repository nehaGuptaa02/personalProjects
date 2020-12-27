import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ProductDetailsPage.module.css';
import Button from '@material-ui/core/Button';
import { withStyles } from '@material-ui/core/styles';
import ShoppingBasketIcon from '@material-ui/icons/ShoppingBasket';
import * as actionCreators from '../../../store/actions/index';
import Spinner from '../../../components/UI/Spinner/Spinner';
const useStyles = theme => ({
    button: {
        color: 'white',
        backgroundColor: '#563f46',
        marginBottom: theme.spacing(6),

    }
});

class ProductDetailsPage extends Component {
    constructor(props) {
        super(props);
        this.addToCartHandler = this.addToCartHandler.bind(this);
        this.buyNowHandler = this.buyNowHandler.bind(this);
        this.viewVariationsHandler = this.viewVariationsHandler.bind(this);

    }
    componentDidMount() {
        // let productDetails = JSON.parse(localStorage.getItem('productDetails'));
        // this.props.onViewProduct(productDetails.productDto.id);
        this.props.onViewProductDetails(this.props.location.state.productId, this.props.location.state.variationId);

    }
    addToCartHandler = () => {
        const token = localStorage.getItem('token');
        // let details = JSON.parse(localStorage.getItem('productDetails'));
        let details=this.props.productDetails
        this.props.onAddToCart(details.productDto.id, details.productVariationDto.id,
            details.productDto.name, details.productVariationDto.price, details.productVariationDto.primaryImageName, token);
    }
    buyNowHandler = () => {
        this.props.history.push('/signIn');
    }
    viewVariationsHandler = (e, productId, variationId) => {
        e.preventDefault();
        console.log('product clicked')
        this.props.onViewProduct(productId);
        this.props.onViewProductDetails(productId, variationId);

    }
    render() {
        const { classes } = this.props;
        let productDetails = this.props.productDetails
        // if(this.props.isAuthenticated)
        // {
        //      productDetails = JSON.parse(localStorage.getItem('productDetails'));
        // }
        console.log('home page products details', this.props.productDetails)
        console.log('home page', this.props.productWithVariations)
        console.log('home page auth', this.props.isAuthenticated)
        let productVariations = this.props.productWithVariations;
        let variations = (<div></div>);
        if (this.props.isAuthenticated && this.props.productDetails != null && this.props.productWithVariations != null) {
            variations = (
                productVariations.productVariationDtoSet.map(variation => (

                    < div
                        key={variation.id} >
                        {
                            Object.entries(variation.metadata).map(([key, value]) => (
                                <div key={variation.id}>
                                    <button
                                        onClick={(e) => this.viewVariationsHandler(e, productDetails.productDto.id, variation.id)}
                                        className={styles.Button}>{key} : {value.toString()}</button>
                                </div>

                            ))
                        }
                    </div >
                ))
            );

        }
        let products = <Spinner />
        if (this.props.productDetails != null) {
            products = (
                <div className={styles.Outer}>
                    <div className={styles.Image}>
                        <img src={productDetails.productVariationDto.primaryImageName} alt="loading..." width="345" height="495"></img>
                    </div>
                    <div className={styles.Details}>
                        <strong><p style={{ fontSize: '20px' }}>{productDetails.productDto.brand}</p></strong>
                        <p>{productDetails.productDto.name}</p>
                        <hr></hr>
                        <br></br>
                        <strong><p>Rs. {productDetails.productVariationDto.price}</p></strong>
                        <br></br>
                        {this.props.isAuthenticated ?
                            <Button
                                onClick={(e) => this.addToCartHandler(e)}
                                className={classes.button}
                                variant="contained" color="inherit"
                                startIcon={<ShoppingBasketIcon />}>
                                Add to Cart </Button>
                            : <Button
                                onClick={(e) => this.buyNowHandler(e)}
                                className={classes.button}
                                variant="contained" color="inherit"
                                startIcon={<ShoppingBasketIcon />}>
                                Buy now </Button>}

                        <br></br>
                        <strong style={{ alignItems: 'left' }}>Product Details</strong>
                        <p>{productDetails.productDto.description}</p>
                        <hr></hr>
                        <br></br>
                        <strong>100% Original Products</strong>
                        <br></br>
                        <strong>Free Delivery on order above Rs. 799</strong>
                        <br></br>
                        <strong>Pay on delivery might be available</strong>
                        <br></br>
                        <strong>Easy 30 days returns and exchanges</strong>
                        <br></br>
                        <strong>Try & Buy might be available</strong>

                    </div>
                    {/* //code maping variations */}
                    <div className={styles.Variations}>
                        {variations}
                    </div>
                </div>
            );
        }
        return (
         <div>
             {products}
         </div>
        );
    }

}

const mapStateToProps = state => {
    return {
        isAuthenticated: state.auth.token !== null,
        // productDetails: state.product.productDetails,
        productDetails: state.product.productDetails,
        productWithVariations: state.product.productWithVariations
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onAddToCart: (productId, variationId, name, price, image, token) => dispatch(actionCreators.addToCart(productId, variationId, name, price, image, token)),
        onViewProduct: (productId) => dispatch(actionCreators.viewProduct(productId)),
        onViewProductDetails: (productId, variationId, token) => dispatch(actionCreators.viewProductDetails(productId, variationId, token)),


    };
}
export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(ProductDetailsPage));