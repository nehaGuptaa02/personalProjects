import React, { Component } from 'react';
import { connect } from 'react-redux';
 import * as actionCreators from '../../../../store/actions/index';
import Aux from '../../../../hoc/Aux/Aux';
import styles from './ViewCustomerProfile.module.css';
import axios from 'axios';
class ViewCustomerProfile extends Component {

    constructor(props) {
        super(props)
        this.state = {
            source: null
        }
        this.updateProfileHandler = this.updateProfileHandler.bind(this)
        this.viewAddressHandler = this.viewAddressHandler.bind(this)
        this.updatePasswordHandler = this.updatePasswordHandler.bind(this)
    }
    componentDidMount()
    {   const token=localStorage.getItem('token');
        this.props.onViewCustomerProfile(token);
    }
    updateProfileHandler=()=>{
        this.props.history.push('/updateCustomerProfile');

    }
    viewAddressHandler=()=>{
        this.props.onViewCustomerAddresses(localStorage.getItem('token'));
        this.props.history.push('/viewCustomerAddresses');

    }
    updatePasswordHandler=()=>{
        this.props.history.push('/updatePassword');

    }
    render() {
        return (
            <Aux className={styles.Root}>
                <div className={styles.Image}>
                    <img src={this.props.imagePath} alt="loading" width="195" height="145" />
                </div >
                <div className={styles.Outer}>
                    <p>ID: {this.props.id}</p>
                    <p>EMAIL: {this.props.email}</p>
                    <p>FIRSTNAME: {this.props.firstName}</p>
                    <p>LASTNAME: {this.props.lastName}</p>
                    <p>CONTACT: {this.props.contact}</p>
                    <button 
                    onClick={this.updateProfileHandler}
                    className={styles.Button}>Update Profile</button>
                     <button 
                    onClick={this.viewAddressHandler}
                    className={styles.Button}>View Address</button>
                     <button 
                    onClick={this.updatePasswordHandler}
                    className={styles.Button}>Update Password</button>
                    
                      </div>
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
        active: state.customer.active,
        imagePath: state.customer.imagePath,
        contact: state.customer.contact,

    };
}
const mapDispatchToProps=dispatch=>{
    return{
    onViewCustomerAddresses:(token)=>dispatch(actionCreators.viewCustomerAddresses(token)),
    onViewCustomerProfile: (token) => dispatch(actionCreators.viewCustomerProfile(token)),
    
    // onGetImage:(imageName)=>dispatch(actionCreators.getImage(imageName))
    };
}
export default connect(mapStateToProps,mapDispatchToProps)(ViewCustomerProfile);