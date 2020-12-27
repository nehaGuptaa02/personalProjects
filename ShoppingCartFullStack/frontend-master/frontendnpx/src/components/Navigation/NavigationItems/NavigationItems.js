import React, { Component } from 'react';
import styles from './NavigationItems.module.css';
import NavigationItem from './NavigationItem/NavigationItem';
import { connect } from 'react-redux';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import * as actionCreators from '../../../store/actions/index';


// const NavigationItems = (props) => {
//     let emailAdd = localStorage.getItem('type');
//     const cartClicked = () => {
//         // window.location.reload(false);
//         console.log('cartClicked');
//         const token = localStorage.getItem('token');
//         props.onAuthOrders(token);
//     }
//     return (
//         <ul className={styles.NavigationItems}>
//             {props.isAuth ? null : <NavigationItem link="/signUpCustomer">SignUp</NavigationItem>}
//             {props.isAuth ? null : <NavigationItem link="/signIn">SignIn</NavigationItem>}
//     {props.isAuth && emailAdd=='customer' ? <NavigationItem link="/cart"><ShoppingCartIcon onClick={cartClicked}></ShoppingCartIcon><sup>{props.authProducts.length}</sup></NavigationItem> : null}
//             {props.isAuth  && emailAdd=='customer'? <NavigationItem link="/checkout">Checkout</NavigationItem> : null}

//         </ul>
//     );
// }
// const mapStateToProps = state => {
//     return {
//         emailAdd: state.auth.email,
//         authProducts: state.auth.authProducts
//     };
// }
// const mapDispatchToProps = dispatch => {
//     return {
//         onAuthOrders: (token) => dispatch(actionCreators.authOrders(token))
//     };
// }
// export default connect(mapStateToProps, mapDispatchToProps)(NavigationItems);


class NavigationItems extends Component {
    componentDidMount()
    {
        const token=localStorage.getItem('token');
        this.props.onAuthOrders(token);
    }
    render() {
        let emailAdd = localStorage.getItem('type');
        const cartClicked = () => {
            // window.location.reload(false);
            console.log('cartClicked');
            const token = localStorage.getItem('token');
            this.props.onAuthOrders(token);
        }
        return (
            <ul className={styles.NavigationItems}>
                {this.props.isAuth ? null : <NavigationItem link="/signUpCustomer">SignUp</NavigationItem>}
                {this.props.isAuth ? null : <NavigationItem link="/signIn">SignIn</NavigationItem>}
                {this.props.isAuth && emailAdd == 'customer' ? <NavigationItem link="/cart"><ShoppingCartIcon onClick={this.cartClicked}></ShoppingCartIcon><sup>{this.props.authOrders.length}</sup></NavigationItem> : null}
                {this.props.isAuth && emailAdd == 'customer' ? <NavigationItem link="/checkout">Checkout</NavigationItem> : null}

            </ul>
        );
    }

}
const mapStateToProps = state => {
    return {
        emailAdd: state.auth.email,
       authOrders:state.auth.authOrders

    };
}
const mapDispatchToProps = dispatch => {
    return {
        onAuthOrders: (token) => dispatch(actionCreators.authOrders(token))
    };
}
export default connect(mapStateToProps, mapDispatchToProps)(NavigationItems);