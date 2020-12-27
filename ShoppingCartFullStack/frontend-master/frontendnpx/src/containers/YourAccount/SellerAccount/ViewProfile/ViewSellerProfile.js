import React, { Component } from 'react';
import { connect } from 'react-redux';
import * as actionCreators from '../../../../store/actions/index';
import Aux from '../../../../hoc/Aux/Aux';
import styles from './ViewSellerProfile.module.css';
import axios from 'axios';
class ViewSellerProfile extends Component {

    constructor(props) {
        super(props)
        this.state = {
            source: null
        }
        this.updateProfileHandler = this.updateProfileHandler.bind(this)
        this.updateAddressHandler = this.updateAddressHandler.bind(this)
        this.updatePasswordHandler = this.updatePasswordHandler.bind(this)
    }
    componentDidMount() {
        const token = localStorage.getItem('token');
        this.props.onViewSellerProfile(token);
    }
    updateProfileHandler = () => {
        this.props.history.push('/updateSellerProfile');

    }
    updateAddressHandler = (e, addressId, city, state, country, zipCode, label, addressLine) => {
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
    updatePasswordHandler = () => {
        this.props.history.replace('/updatePassword');
    }
    render() {
        let address = (
            <button>Add address</button>
        );
        if (this.props.sellerAddress) {
            address = (<div className={styles.Outer}>
                <p>Address associated with the given seller</p>
                {this.props.sellerAddress.map(sellerAddress =>
                    <div key={sellerAddress.id}>
                        <p>ADDRESS ID : {sellerAddress.id}</p>
                        <p>CITY : {sellerAddress.city}</p>
                        <p>COUNTRY : {sellerAddress.country}</p>
                        <p>STATE : {sellerAddress.state}</p>
                        <p>ZIPCODE : {sellerAddress.zipCode}</p>
                        <p>LABEL : {sellerAddress.label}</p>
                        <p>ADDRESS LINE : {sellerAddress.addressLine}</p>
                        <button
                            onClick={(e) => this.updateAddressHandler(e, sellerAddress.id, sellerAddress.city, sellerAddress.state, sellerAddress.country, sellerAddress.zipCode, sellerAddress.label, sellerAddress.addressLine)}
                            className={styles.Button}>Update Address</button>
                    </div>
                )

                }

            </div>);
        }
        return (
            <Aux className={styles.Root}>
                <div className={styles.Image}>
                    <img src={this.props.imagePath} alt='loading' width="200" height="150" />
                </div >
                <div className={styles.Outer}>
                    <p>ID: {this.props.id}</p>
                    <p>EMAIL: {this.props.email}</p>
                    <p>FIRSTNAME: {this.props.firstName}</p>
                    <p>LASTNAME: {this.props.lastName}</p>
                    <p>CONTACT: {this.props.contact}</p>
                    <p>COMPANY NAME: {this.props.companyName}</p>
                    <p>COMPANY CONTACT: {this.props.companyContact}</p>
                    <p>GST: {this.props.gst}</p>

                    <button
                        onClick={this.updateProfileHandler}
                        className={styles.Button}>Update Profile</button>
                    <button
                        onClick={this.updatePasswordHandler}
                        className={styles.Button}>Update Password</button>
                </div>
                {address}
            </Aux>

        );
    }
}

const mapStateToProps = state => {
    return {
        id: state.customer.id,
        firstName: state.customer.firstName,
        lastName: state.customer.lastName,
        email: state.customer.email,
        imagePath: state.customer.imagePath,
        contact: state.customer.contact,
        companyName: state.customer.companyName,
        companyContact: state.customer.companyContact,
        gst: state.customer.gst,
        sellerAddress: state.customer.sellerAddress
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onViewSellerProfile: (token) => dispatch(actionCreators.viewSellerProfile(token)),
        // onGetImage:(imageName)=>dispatch(actionCreators.getImage(imageName))
    };
}
export default connect(mapStateToProps, mapDispatchToProps)(ViewSellerProfile);