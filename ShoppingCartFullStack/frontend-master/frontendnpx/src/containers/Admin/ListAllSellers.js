import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ListAllSellers.module.css';
import * as actionCreators from '../../store/actions/index';
class ListAllSellers extends Component {
    constructor(props) {
        super(props)
        this.activateSellerHandler = this.activateSellerHandler.bind(this)
        this.deactivateSellerHandler = this.deactivateSellerHandler.bind(this)

    }
    componentDidMount()
    {
        const token=localStorage.getItem('token');
        this.props.onListAllSellers(token);
    }
    activateSellerHandler = (id) => {
        let choice = 'activate';
        const token = localStorage.getItem('token');
        this.props.onActivateDeactivateCustomer(id, choice, token);
    }
    deactivateSellerHandler = (id) => {
        let choice = 'deactivate';
        const token = localStorage.getItem('token');
        this.props.onActivateDeactivateCustomer(id, choice, token);
    }
    render() {
        let sellersList = this.props.sellers.map(seller => (
            <div className={styles.Div}>
                <ol>
                    <p>ID: {seller.id}</p>
                    <p>FIRSTNAME: {seller.firstName}</p>
                    <p>LASTNAME: {seller.lastName} </p>
                    <p>EMAIL: {seller.email} </p>
                    <p>COMPANY NAME: {seller.companyName} </p>
                    <p>COMPANY CONTACT: {seller.companyContact} </p>
                    {/* <p>ISACTIVE: {seller.active} </p> */}
                    <button
                        className={styles.Button}
                        onClick={() => this.activateSellerHandler(seller.id)}>Activate</button>
                    <button
                        className={styles.Button}
                        onClick={() => this.deactivateSellerHandler(seller.id)}>Deactivate</button>
                </ol>
            </div>
        ))
        return (
            <div>

                {this.props.sellers ? <h2 style={{ color: '#563f46', textAlign: 'center' }}>List of all sellers</h2> : null}
                {sellersList}
            </div>

        );
    }

}
const mapStateToProps = state => {
    return {
        sellers: state.admin.sellers
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onActivateDeactivateCustomer: (userId, choice, token) => dispatch(actionCreators.activateDeactivateCustomer(userId, choice, token)),
        onListAllSellers: (token) => dispatch(actionCreators.listAllSellers(token)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ListAllSellers);