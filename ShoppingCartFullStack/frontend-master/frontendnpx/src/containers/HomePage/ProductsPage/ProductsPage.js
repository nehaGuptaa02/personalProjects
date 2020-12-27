import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ProductsPage.module.css';
import * as actionCreators from '../../../store/actions/index';
class ProductsPage extends Component {
    constructor(props) {
        super(props);
        this.productDetailsHandler = this.productDetailsHandler.bind(this);

    }
    componentDidMount() {
        const token = localStorage.getItem('token');
        // const subCategoryId = localStorage.getItem('subCategoryId');
        console.log('componentDidMount')
        this.props.onViewAllProducts(this.props.location.state.subCategoryId,token)
    }
    productDetailsHandler = (e,productId, variationId) => {
        this.props.onViewProduct(productId);
        this.props.onViewProductDetails(productId, variationId);
        // if (this.props.productDetails != null) {
        //     this.props.history.push('/productDetailsPage');
        // }
        if (this.props.productDetails != null) {
            e.preventDefault()
            this.props.history.push({
                pathname: '/productDetailsPage',
                state: {
                    productId: productId,
                    variationId:variationId
                }
            })
        }
    }
    render() {
        // let  products = JSON.parse(localStorage.getItem('childCategories'));
        // console.log('Products Page',this.props.products);
        let products = this.props.products
        console.log('Products Page',this.props.products);
        // let productList = (
        //     products.map(product => (
        //         <div key={product.id} className={styles.Div}>
        //             {product.productVariationDtoSet.map(variation => (
        //                 <div key={variation.id}>
        //                     <div
        //                         onClick={() => this.productDetailsHandler(product.id, variation.id)}
        //                         className={styles.Image}>
        //                         <img src={variation.primaryImageName} alt="loading..." width="300" height="300"></img>
        //                     </div>
        //                     <div className={styles.Inner}>
        //                         <p style={{ fontSize: '20px' }}><strong>{product.brand}
        //                             <br></br>
        //                             <strong> Rs.</strong>{variation.price}</strong>{product.name}</p>
        //                         <p><strong></strong></p>
        //                     </div>
        //                 </div>
        //             ))}
        //         </div>

        //     ))
        // );
        // let variation=product.productVariationDtoSet[0];
        let productList = (
            products.map(product => (
                <div key={product.id} className={styles.Div}>
                    {
                    // product.productVariationDtoSet.map(variation => (
                        <div key={product.productVariationDtoSet[0].id}>
                            <div
                                onClick={(e) => this.productDetailsHandler(e,product.id, product.productVariationDtoSet[0].id)}
                                className={styles.Image}>
                                <img src={product.productVariationDtoSet[0].primaryImageName} alt="loading..." width="300" height="300"></img>
                            </div>
                            <div className={styles.Inner}>
                                <p style={{ fontSize: '20px' }}><strong>{product.brand}
                                    <br></br>
                                    <strong> Rs.</strong>{product.productVariationDtoSet[0].price}</strong>{product.name}</p>
                                <p><strong></strong></p>
                            </div>
                        </div>
                    }
                </div>

            ))
        );

        return (
            <div>
                <strong><h1 style={{ color: '#563f46', textAlign: 'center', marginBottom: '60px', marginTop: '50px' }}>
                    Hurraah!!Pick up your favourite Styles.</h1></strong>
                {productList}
            </div>
        );
    }

}

const mapStateToProps = state => {
    return {
        products: state.product.products,
        productDetails: state.product.productDetails
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onViewProductDetails: (productId, variationId, token) => dispatch(actionCreators.viewProductDetails(productId, variationId, token)),
        onViewAllProducts: (subCategoryId, token) => dispatch(actionCreators.viewAllProducts(subCategoryId, token)),
        onViewProduct:(productId)=>dispatch(actionCreators.viewProduct(productId))
    };
}
export default connect(mapStateToProps, mapDispatchToProps)(ProductsPage);