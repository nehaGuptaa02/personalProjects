import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import { withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import styles from './Admin.module.css';
import { connect } from 'react-redux';
import * as actionCreators from '../../store/actions/index';
const useStyles = theme => ({
    button: {
        position: 'relative',
        backgroundColor: '#563f46',
        color: 'white',
        textAlign: 'center',
        height: '60px',
        width: '180px',
        margin: '30px',
        display: 'inline-block',
        '&:hover': {
            background: '#A29599',
        }
    },
    typography: {
        padding: '5px',
        color: '#563f46'

    },
    cancel: {
        margin: '40px',
        color: 'black',
        backgroundColor: '#A29599'

    }
});

class Admin extends Component {
    listAllCustomersHandler = () => {
        var token = localStorage.getItem('token');
        this.props.onListAllCustomers(token)
        if (this.props.customers != null) {
            this.props.history.replace('/listAllCustomers')
        }

    }
    listAllSellersHandler = () => {
        var token = localStorage.getItem('token');
        this.props.onListAllSellers(token)
        if (this.props.sellers != null) {
            this.props.history.replace('/listAllSellers')
        }
    }
    listAllProductsHandler = () => {
        var token = localStorage.getItem('token');
        this.props.onListAllProducts(token)
        this.props.history.push('/listAllProducts');

    }
    cancelHandler = () => (
        this.props.history.goBack()
    )
    render() {
        const { classes } = this.props;
        return (
            <div className={styles.Admin}>
                <Typography
                    className={classes.typography}
                    variant="h4" align="center">
                    Welcome Admin!!
                </Typography>
                <Button
                    onClick={this.listAllCustomersHandler}
                    className={classes.button}
                    variant="contained" >List all Customers</Button>
                <Button
                    onClick={this.listAllSellersHandler}
                    className={classes.button}
                    variant="contained" >List all Sellers</Button>
                <Button
                    onClick={this.listAllProductsHandler}
                    className={classes.button}
                    variant="contained" >List All Products</Button>
                <br></br>
                <Button
                    onClick={this.cancelHandler}
                    className={classes.cancel}
                    variant="contained" >Cancel</Button>
            </div>
        );
    }
}
const mapStateToProps = state => {
    return {
        customers:state.admin.customers,
        sellers: state.admin.sellers
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onListAllCustomers: (token) => dispatch(actionCreators.listAllCustomers(token)),
        onListAllSellers: (token) => dispatch(actionCreators.listAllSellers(token)),
        onListAllProducts: (token) => dispatch(actionCreators.listAllProducts(token))
    };
}
export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(Admin));