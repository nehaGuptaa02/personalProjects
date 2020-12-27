import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ViewAllProducts.module.css';
import * as actionCreators from '../../../../store/actions/index';
class ViewAllProducts extends Component {

    constructor(props) {
        super(props)
        this.addVariationHandler = this.addVariationHandler.bind(this)
        this.viewVariationsHandler = this.viewVariationsHandler.bind(this)
        this.deleteProductHandler = this.deleteProductHandler.bind(this)
        this.updateProductHandler = this.updateProductHandler.bind(this)
        this.addProductHandler = this.addProductHandler.bind(this)

    }
    componentDidMount() {
        const token = localStorage.getItem('token');
        this.props.onViewAllProductsSeller(token);
    }
    addVariationHandler = (e, id) => {
        e.preventDefault()
        this.props.history.push({
            pathname: '/addProductVariation',
            state: {
                productId: id,
            }
        })

    }

    viewVariationsHandler = (id) => {
        const token = localStorage.getItem('token');
        this.props.onViewProductVariations(id, token);
        this.props.history.push('/viewProductVariationsBySeller');
    }
    deleteProductHandler = (id) => {
        const token = localStorage.getItem('token');
        this.props.onDeleteProduct(id, token);
    }
    updateProductHandler = (e, id) => {
        e.preventDefault()
        this.props.history.push({
            pathname: '/updateProduct',
            state: {
                productId: id,
            }
        })
    }
    addProductHandler = () => {
        console.log('Add Product clicked');
        this.props.history.push('/addProduct');
    }
    render() {
        let productsList = this.props.sellerProducts.map(product => (
            <div key={product.id} className={styles.Div}>
                <ol>
                    <p>ID: {product.id}</p>
                    <p>NAME: {product.name}</p>
                    <p>BRAND: {product.brand} </p>
                    <p>DESCRIPTION :{product.description}</p>
                    <p>CATEGORY :{product.categoryDto.name}</p>

                </ol>
                <button
                    className={styles.Button}
                    onClick={(e) => this.addVariationHandler(e, product.id)}>Add Variation</button>
                <button
                    className={styles.Button}
                    onClick={() => this.viewVariationsHandler(product.id)}>View Variations</button>
                <button
                    className={styles.Button}
                    onClick={() => this.deleteProductHandler(product.id)}>Delete Product</button>
                <button
                    className={styles.Button}
                    onClick={(e) => this.updateProductHandler(e, product.id)}>Update Product</button>
            </div>

        ))

        return (
            <div>
                <h2 style={{ color: '#563f46', textAlign: 'center' }}>List of all Products</h2>
                {/* <div className={styles.AddProduct}>
                </div> */}
                <button
                    onClick={this.addProductHandler}
                    className={styles.AddProduct}>Add Product</button>
                {productsList}
            </div>
        );
    }

}
const mapStateToProps = state => {
    return {
        sellerProducts: state.product.sellerProducts
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onViewAllProductsSeller: (token) => dispatch(actionCreators.viewAllProductsSeller(token)),
        onDeleteProduct: (id, token) => dispatch(actionCreators.deleteProduct(id, token)),
        onViewProductVariations: (id, token) => dispatch(actionCreators.viewProductVariations(id, token)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ViewAllProducts);