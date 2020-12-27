import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ViewProductDetails.module.css';
import * as actionCreators from '../../../../store/actions/index';
class ViewProductDetails extends Component {
    render() {

        let productsVariationList = this.props.productDetails.map(variation => (
            <div key={variation.id} className={styles.Div}>
                <ol>
                    <p><strong>ID: </strong>{variation.id}</p>
                    <p><strong>PRICE:</strong> {variation.price}</p>
                    <p><strong>QUANTIY AVAILABLE:</strong> {variation.quantityAvailable} </p>
                    {/* <p>METADATA :{variation.metadata}</p> */}
                </ol>
                <div className={styles.Image}>
                    <img src={variation.primaryImageName} alt="loading" width="95" height="95"></img>
                </div>
            </div>

        ))

        return (
            <div>
                <h2 style={{ color: '#563f46', textAlign: 'center' }}>All the variations available for this product.</h2>
                {productsVariationList}
            </div>
        );
    }

}
const mapStateToProps = state => {
    return {
        productDetails: state.admin.productDetails
    };
}
// const mapDispatchToProps = dispatch => {
//     return {
//         onAdminViewProductDetails: (productId, token) => dispatch(actionCreators.adminViewProductDetails(productId, token)),
//     };
// }
export default connect(mapStateToProps)(ViewProductDetails);