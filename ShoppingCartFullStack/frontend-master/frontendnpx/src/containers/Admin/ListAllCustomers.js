import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ListAllCustomers.module.css';
import * as actionCreators from '../../store/actions/index';
class ListAllCustomers extends Component {

    constructor(props) {
        super(props)
        this.activateCustomerHandler = this.activateCustomerHandler.bind(this)
        this.deactivateCustomerHandler = this.deactivateCustomerHandler.bind(this)

    }
    componentDidMount()
    {
        const token=localStorage.getItem('token');
        this.props.onListAllCustomers(token);
    }
    activateCustomerHandler = (id) => {
        let choice = 'activate';
        const token = localStorage.getItem('token');
        this.props.onActivateDeactivateCustomer(id, choice, token);
    }
    deactivateCustomerHandler = (id) => {
        let choice = 'deactivate';
        const token = localStorage.getItem('token');
        this.props.onActivateDeactivateCustomer(id, choice, token);
    }

    render() {
        let customersList = this.props.customers.map(customer => (
            <div className={styles.Div}>
                <ol>
                    <p>ID: {customer.id}</p>
                    <p>FIRSTNAME: {customer.firstName}</p>
                    <p>LASTNAME: {customer.lastName} </p>
                    <p>EMAIL: {customer.email} </p>
                </ol>
                <button
                    className={styles.Button}
                    onClick={() => this.activateCustomerHandler(customer.id)}>Activate</button>
                <button
                    className={styles.Button}
                    onClick={() => this.deactivateCustomerHandler(customer.id)}>Deactivate</button>
            </div>

        ))

        return (
            <div>
                <h2 style={{ color: '#563f46', textAlign: 'center' }}>List of all customers</h2>
                {/* <Pagination
                    bsSize="medium"
                    items={10}
                    activePage={1}/> */}
                {customersList}
            </div>
        );
    }

}
const mapStateToProps = state => {
    return {
        customers: state.admin.customers
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onActivateDeactivateCustomer: (userId, choice, token) => dispatch(actionCreators.activateDeactivateCustomer(userId, choice, token)),
        onListAllCustomers: (token) => dispatch(actionCreators.listAllCustomers(token)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ListAllCustomers);