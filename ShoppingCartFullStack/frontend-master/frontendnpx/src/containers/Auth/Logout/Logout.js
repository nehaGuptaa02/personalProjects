//This will help in logging out and redirecting the  page to the initial home page.
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import * as actionCreators from '../../../store/actions/index';  
class Logout extends Component {
    componentDidMount()
    {
        this.props.onLogout();
    }
    render() {
        return <Redirect to="/"/>//It will redirect to the initial page
    }
}
const mapDispatchToProps=dispatch=>{
    return{
     onLogout:()=>dispatch(actionCreators.authLogout())
    };
}
export default connect(null,mapDispatchToProps)(Logout);