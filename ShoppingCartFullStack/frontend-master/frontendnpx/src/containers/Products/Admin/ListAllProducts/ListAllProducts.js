import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ListAllProducts.module.css';
import * as actionCreators from '../../../../store/actions/index';
class ListAllProducts extends Component {

    constructor(props) {
        super(props)
        this.activateProductHandler = this.activateProductHandler.bind(this)
        this.deactivateProductHandler = this.deactivateProductHandler.bind(this)
        this.viewProductDetailsHandler = this.viewProductDetailsHandler.bind(this)

    }
    componentDidMount()
    {
        const token=localStorage.getItem('token');
        this.props.onListAllProducts(token);
    }
    viewProductDetailsHandler = (id) => {
        const token = localStorage.getItem('token');
        this.props.onAdminViewProductDetails(id, token);
        this.props.history.push('/adminViewProductDetails');

    }
    activateProductHandler = (id) => {
        let choice = 'activate';
        const token = localStorage.getItem('token');
        this.props.onActivateDeactivateProduct(id, choice, token);
    }
    deactivateProductHandler = (id) => {
        let choice = 'deactivate';
        const token = localStorage.getItem('token');
        this.props.onActivateDeactivateProduct(id, choice, token);
    }

    render() {
        let productsList = this.props.allProducts.map(product => (
            <div key={product.id} className={styles.Div}>
                <ol>
                    <p>ID: {product.id}</p>
                    <p>NAME: {product.name}</p>
                    <p>DESCRIPTION: {product.description} </p>
                    <p>CATEGORY :{product.categoryDto.name}</p>
                </ol>
                <div className={styles.Image}>
                    <img src={product.productVariationDtoSet[0].primaryImageName} alt="loading" width="95" height="95"></img>
                </div>
                <button
                    className={styles.Button}
                    onClick={() => this.viewProductDetailsHandler(product.id)}>View Details</button>
                <button
                    className={styles.Button}
                    onClick={() => this.activateProductHandler(product.id)}>Activate Product</button>
                <button
                    className={styles.Button}
                    onClick={() => this.deactivateProductHandler(product.id)}>Deactivate Product</button>
            </div>

        ))

        return (
            <div>
                <h2 style={{ color: '#563f46', textAlign: 'center' }}>List of all Products</h2>
                {productsList}
            </div>
        );
    }

}
const mapStateToProps = state => {
    return {
        allProducts: state.admin.allProducts
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onActivateDeactivateProduct: (productId, choice, token) => dispatch(actionCreators.activateDeactivateProduct(productId, choice, token)),
        onAdminViewProductDetails: (productId, token) => dispatch(actionCreators.adminViewProductDetails(productId, token)),
        onListAllProducts: (token) => dispatch(actionCreators.listAllProducts(token))

    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ListAllProducts);