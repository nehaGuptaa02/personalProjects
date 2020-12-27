import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './Cart.module.css';
import Button from '@material-ui/core/Button';
import { withStyles } from '@material-ui/core/styles';
import ShoppingBasketIcon from '@material-ui/icons/ShoppingBasket';
import DoneIcon from '@material-ui/icons/Done';
import * as actionCreators from '../../store/actions/index';
const useStyles = theme => ({
    button: {
        color: 'white',
        backgroundColor: '#563f46',
        marginTop: theme.spacing(2),
        // marginBottom: theme.spacing(12),
        marginLeft: theme.spacing(126),

    }
});

class Cart extends Component {
    constructor(props) {
        super(props);
        this.checkoutHandler = this.checkoutHandler.bind(this);
    }
    componentDidMount() {
        const token = localStorage.getItem('token');
        this.props.onAuthOrders(token);

    }
    checkoutHandler = () => {
        const token = localStorage.getItem('token');
        this.props.history.push('/checkout');
    }
    deleteOrderHandler = (id, e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        this.props.onDeleteOrder(id, token);
        // window.location.reload(false);


    }
    render() {
        console.log('cart', this.props.authOrders);
        const { classes } = this.props;
        // let orders = JSON.parse(localStorage.getItem('orders'));
        let orders = this.props.authOrders
        let totalPrice = 0;
        console.log('orders', orders);
        let ordersList = (
            <div>
                <strong style={{ textAlign: 'center' }}><h1>No orders found yet</h1></strong>
            </div>
        );
        if (orders != null) {
            ordersList = orders.map(order => (
                <div
                    key={order.id}
                    className={styles.Outer}>
                    <div className={styles.Image}>
                        <img src={order.imageName} alt="loading..." width="145" height="145"></img>
                    </div>
                    <div className={styles.Details}>
                        <strong><p style={{ fontSize: '20px' }}>{order.name}</p></strong>
                        <strong><p>QUANTITY. {order.quantity}</p></strong>
                        <strong><p>Rs. {order.price}</p></strong>
                        <button
                            onClick={(e) => this.deleteOrderHandler(order.id, e)}
                            className={styles.Delete}
                        >Delete</button>
                        <hr></hr>
                    </div>
                </div>
            ))
            if (orders != null) {

                orders.map(order => (
                    totalPrice = totalPrice + order.price
                ))

            }

        }
        console.log('orrr', orders)
        console.log(orders.length != 0)

        let display = (<div>
            <h1 className={styles.Empty}>Your Shopping Cart is empty. </h1>
        </div>);

        if (orders.length != 0) {
            display = (
                <div>
                    <Button
                        onClick={(e) => this.checkoutHandler(e)}
                        className={classes.button}
                        variant="contained" color="inherit"
                        startIcon={<DoneIcon />}>
                        Checkout </Button>
                    <strong
                        className={styles.Price}
                    >Total Price : Rs.{totalPrice}</strong>
                </div>)
        }
        return (
            <div>
                {ordersList}
                {display}
            </div>

        );
    }

}
const mapStateToProps = state => {
    return {
        authOrders: state.auth.authOrders
    };
}

const mapDispatchToProps = dispatch => {
    return {
        onDeleteOrder: (id, token) => dispatch(actionCreators.deleteOrder(id, token)),
        onAuthOrders: (token) => dispatch(actionCreators.authOrders(token))

    };
}
export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(Cart));