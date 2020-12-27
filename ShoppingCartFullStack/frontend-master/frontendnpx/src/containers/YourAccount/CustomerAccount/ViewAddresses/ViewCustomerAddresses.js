import React, { Component } from 'react';
import { connect } from 'react-redux';
import * as actionCreators from '../../../../store/actions/index';
import styles from './ViewCustomerAddresses.module.css';
import AddIcon from '@material-ui/icons/Add';
import { Link } from 'react-router-dom';
class ViewCustomerAddresses extends Component {

    constructor(props) {
        super(props)
        this.addAddressHandler = this.addAddressHandler.bind(this)
        this.deleteAddressHandler = this.deleteAddressHandler.bind(this)
        this.updateAddressHandler = this.updateAddressHandler.bind(this)

    }
    componentDidMount() {
        const token = localStorage.getItem('token');
        this.props.onViewCustomerAddresses(token);
    }
    addAddressHandler = () => {
        this.props.history.replace('/addAddress')
    }
    deleteAddressHandler = (id) => {
        const token = localStorage.getItem('token');
        this.props.onDeleteAddress(id, token);
        // this.props.onViewCustomerAddresses(token);

    }
    updateAddressHandler = (e,addressId,city,state,country,zipCode,label,addressLine) => {
        e.preventDefault()
        console.log('update address');
        this.props.history.push({
            pathname: '/updateAddress',
            state: { 
                addressId: addressId,
                city: city,
                state: state,
                country: country,
                zipCode: zipCode,
                label: label,
                addressLine: addressLine
            }
          })
        // this.props.history.push('/updateAddress');
    }
    render() {
        let addressesList = this.props.addresses.map(address => (
            <div key={address.id} className={styles.Div}>
                <p>ID: {address.id}</p>
                <p>CITY: {address.city}</p>
                <p>STATE: {address.state}</p>
                <p>COUNTRY: {address.country}</p>
                <p>ZIP CODE: {address.zipCode}</p>
                <p>LABEL: {address.label}</p>
                <p>ADDRESS LINE: {address.addressLine}</p>

                <button
                    className={styles.Button}
                    onClick={() => this.deleteAddressHandler(address.id)}>Delete</button>
                <button
                    className={styles.Button}
                    onClick={(e)=>this.updateAddressHandler(e,address.id,address.city,address.state,address.country,address.zipCode,address.label,address.addressLine)}>Update</button>
            </div>

        ))
        return (
            <div className={styles.Root}>
                <div className={styles.Add} onClick={this.addAddressHandler}>
                    <AddIcon fontSize='inherit' />
                Add Address
                </div>
                {addressesList}
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        addresses: state.customer.addresses
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onDeleteAddress: (id, token) => dispatch(actionCreators.deleteAddress(id, token)),
        onViewCustomerAddresses: (token) => dispatch(actionCreators.viewCustomerAddresses(token))


    };
}
export default connect(mapStateToProps, mapDispatchToProps)(ViewCustomerAddresses)