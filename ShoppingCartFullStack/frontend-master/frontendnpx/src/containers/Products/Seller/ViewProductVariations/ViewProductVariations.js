import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ViewProductVariations.module.css';
import * as actionCreators from '../../../../store/actions/index';
class ViewProductVariations extends Component {
    updateVariationHandler=(e,id)=>{
        e.preventDefault()
        this.props.history.push({
            pathname: '/updateProductVariation',
            state: { 
                variationId: id,
            }
          })
    }
    render() {
        let productsVariationList = this.props.sellerProductVariations.map(variation => (
            <div key={variation.id} className={styles.Div}>
                <ol>
                    <p><strong>ID: </strong>{variation.id}</p>
                    <p><strong>PRICE:</strong> {variation.price}</p>
                    <p><strong>QUANTIY AVAILABLE:</strong> {variation.quantityAvailable} </p>
                </ol>
                <div className={styles.Image}>
                    <img src={variation.primaryImageName} alt="loading" width="95" height="95"></img>
                </div>
                <button
                    className={styles.Button}
                    onClick={(e) => this.updateVariationHandler(e,variation.id)}>Update Variation</button>
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
        sellerProductVariations: state.product.sellerProductVariations
    };
}
export default connect(mapStateToProps)(ViewProductVariations);