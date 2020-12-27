import React, { Component } from 'react';
import Layout from './hoc/Layout/Layout';
import './App.css';
import { Route, Switch, Redirect } from 'react-router-dom';
import SignUpCustomer from './containers/Auth/SignUp/SignUpCustomer';
import SignUpSeller from './containers/Auth/SignUp/SignUpSeller';
import SignInUser from './containers/Auth/SignIn/SignIn';
import Logout from './containers/Auth/Logout/Logout';
import HomePage from './containers/HomePage/HomePage';
import ActivationLink from './containers/ActivationLink/ActivationLink';
import ResendActivationLink from './containers/ActivationLink/ResendActivationLink/ResendActivationLink';
import ForgotPasswordLink from './containers/Auth/ForgotPassword/ForgotPassword';
import ForgotPasswordReset from './containers/Auth/ForgotPassword/ForgotPasswordReset';
import Admin from './containers/Admin/Admin';
import ListAllCustomers from './containers/Admin/ListAllCustomers';
import ListAllSellers from './containers/Admin/ListAllSellers';
import ViewCustomerProfile from './containers/YourAccount/CustomerAccount/ViewProfile/ViewCustomerProfile';
import ViewCustomerAddresses from './containers/YourAccount/CustomerAccount/ViewAddresses/ViewCustomerAddresses';
import AddAddress from './containers/YourAccount/Address/AddAddress';
import UpdatePassword from './containers/YourAccount/UpdatePassword/UpdatePassword';
import UpdateAddress from './containers/YourAccount/Address/UpdateAddress';
import UpdateCustomerProfile from './containers/YourAccount/CustomerAccount/UpdateProfile/UpdateCustomerProfile';
import ViewSellerProfile from './containers/YourAccount/SellerAccount/ViewProfile/ViewSellerProfile';
import UpdateSellerProfile from './containers/YourAccount/SellerAccount/UpdateProfile/UpdateProfile';
import AdminCategory from './containers/Categories/AdminCategory/AdminCategory';
import AddMetadataField from './containers/Categories/AdminCategory/AddMetadataField/AddMetadataField';
import ViewAllMetadaFields from './containers/Categories/AdminCategory/ViewAllMetadataFields/ViewAllMetadataFields';
import AddCategory from './containers/Categories/AdminCategory/AddCategory/AddCategory';
import ViewCategory from './containers/Categories/AdminCategory/ViewCategory/ViewCategory';
import DetailsOfCategory from './containers/Categories/AdminCategory/ViewCategory/DetailsOfCategory';
import ViewAllCategories from './containers/Categories/AdminCategory/ViewAllCategories/ViewAllCategories';
import UpdateCategory from './containers/Categories/AdminCategory/UpdateCategory/UpdateCategory';
import ProductsPage from './containers/HomePage/ProductsPage/ProductsPage';
import ProductDetailsPage from './containers/HomePage/ProductDetailPage/ProductDetailsPage';
import ListAllProducts from './containers/Products/Admin/ListAllProducts/ListAllProducts';
import ViewProductDetails from './containers/Products/Admin/ViewProductDetails/ViewProductDetails';
import AddProduct from './containers/Products/Seller/AddProduct/AddProduct';
import ViewAllProductsBySeller from './containers/Products/Seller/ViewAllProducts/ViewAllProducts';
import ViewAllProductVariationsBySeller from './containers/Products/Seller/ViewProductVariations/ViewProductVariations';
import UpdateProduct from './containers/Products/Seller/UpdateProduct/UpdateProduct';
import AddProductVariation from './containers/Products/Seller/AddProductVariation/AddProductVariation';
import UpdateProductVariation from './containers/Products/Seller/UpdateProductVariation/UpdateProductVariation';
import Checkout from './containers/Checkout/Checkout';
import NotFound from './components/Error/NotFound';
import Cart from './containers/Cart/Cart';
import { connect } from 'react-redux';
import * as actionTypes from './store/actions/index';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure();
class App extends Component {
  componentDidMount() {
    this.props.persistLoginState();//This will persist the login state
  }
  render() {
    const type = localStorage.getItem('type');

    // if (!this.props.isAutheticated) {
    //   let routes = (
    //     <Switch>

    //       <Redirect to="/"></Redirect>
    //     </Switch>
    //   );
    // }
    let routes = (
      <Switch>
        <Route path="/" exact component={HomePage}></Route>
        <Route path="/signUpCustomer" exact component={SignUpCustomer}></Route>
        <Route path="/signUpSeller" exact component={SignUpSeller}></Route>
        <Route path="/signIn" exact component={SignInUser}></Route>
        <Route path="/activateAccount" exact component={ActivationLink}></Route>
        <Route path="/resendActivationLink" exact component={ResendActivationLink}></Route>
        <Route path="/forgotPasswordLink" exact component={ForgotPasswordLink}></Route>
        <Route path="/forgotPasswordReset" exact component={ForgotPasswordReset}></Route>
        <Route path="/signOut" exact component={Logout}></Route>
        <Route path="/admin" exact component={Admin}></Route>
        <Route path="/listAllCustomers" exact component={ListAllCustomers}></Route>
        <Route path="/listAllSellers" exact component={ListAllSellers}></Route>
        <Route path="/viewCustomerProfile" exact component={ViewCustomerProfile}></Route>
        <Route path="/viewCustomerAddresses" exact component={ViewCustomerAddresses}></Route>
        <Route path="/addAddress" exact component={AddAddress}></Route>
        <Route path="/updateAddress" exact component={UpdateAddress}></Route>
        <Route path="/updatePassword" exact component={UpdatePassword}></Route>
        <Route path="/updateCustomerProfile" exact component={UpdateCustomerProfile}></Route>
        <Route path="/viewSellerProfile" exact component={ViewSellerProfile}></Route>
        <Route path="/updateSellerProfile" exact component={UpdateSellerProfile}></Route>
        <Route path="/adminCategory" exact component={AdminCategory}></Route>
        <Route path="/addMetadataField" exact component={AddMetadataField}></Route>
        <Route path="/viewAllMetadataFields" exact component={ViewAllMetadaFields}></Route>
        <Route path="/addCategory" exact component={AddCategory}></Route>
        <Route path="/viewCategory" exact component={ViewCategory}></Route>
        <Route path="/detailsOfCategory" exact component={DetailsOfCategory}></Route>
        <Route path="/viewAllCategories" exact component={ViewAllCategories}></Route>
        <Route path="/updateCategory" exact component={UpdateCategory}></Route>
        <Route path="/productsPage" exact component={ProductsPage}></Route>
        <Route path="/productDetailsPage" exact component={ProductDetailsPage}></Route>
        <Route path="/listAllProducts" exact component={ListAllProducts}></Route>
        <Route path="/adminViewProductDetails" exact component={ViewProductDetails}></Route>
        <Route path="/addProduct" exact component={AddProduct}></Route>
        <Route path="/viewAllProductsBySeller" exact component={ViewAllProductsBySeller}></Route>
        <Route path="/viewProductVariationsBySeller" exact component={ViewAllProductVariationsBySeller}></Route>
        <Route path="/updateProduct" exact component={UpdateProduct}></Route>
        <Route path="/addProductVariation" exact component={AddProductVariation}></Route>
        <Route path="/updateProductVariation" exact component={UpdateProductVariation}></Route>
        <Route path="/cart" exact component={Cart}></Route>
        <Route path="/checkout" exact component={Checkout}></Route>
        {/* <Route path="" component={NotFound} /> */}

      </Switch>
    );
    return (
      <div>
        <Layout>
          {routes}
        </Layout>

      </div>

    );
  }
}
const mapStateToProps = state => {
  return {
    isAutheticated: state.auth.token !== null
  };
}
const mapDispatchToProps = dispatch => {
  return {
    persistLoginState: () => dispatch(actionTypes.authCheckState())
  };
}

//We can connect this with withRouterConnect(which is a higher order component) if the routing causes an error
export default connect(mapStateToProps, mapDispatchToProps)(App);
