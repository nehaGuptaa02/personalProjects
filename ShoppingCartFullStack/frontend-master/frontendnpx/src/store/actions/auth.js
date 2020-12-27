import * as actionTypes from './actionTypes';
import axios from 'axios';
import { toast } from 'react-toastify';
import { adminListAllProductsSuccess } from './admin';
export const authStart = () => {
    return {
        type: actionTypes.AUTH_START
    };
}
export const authSuccess = (token, email, responseStatus) => {
    return {
        type: actionTypes.AUTH_SUCCESS,
        responseStatus: responseStatus,
        idToken: token,
        email: email
    };
}
export const authFail = (error, errorStatus) => {
    return {
        type: actionTypes.AUTH_FAIL,
        error: error,
        errorStatus: errorStatus
    };
}
export const authCheckTimeOut = (expirationTime) => {
    //It will expire the session after 1 hour and we are calling setTimeOut which is an async function
    return dispatch => {
        setTimeout(() => {
            dispatch(authLogout())
        }, expirationTime * 1000);//Here we pass time in miliseconds and from response.data we get in seconds 
    }
}

export const authType = (token) => {
    return dispatch => {
        dispatch(authStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/typeOfUser',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        }).then(response => {
            localStorage.setItem('type', response.data);
        }).catch(error => {
            alert(error.response.data)
            dispatch(authFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}

export const authFirstName = (token) => {
    return dispatch => {
        dispatch(authStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/firstName',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        }).then(response => {
            localStorage.setItem('firstName', response.data);
        }).catch(error => {
            alert(error.response.data)
            dispatch(authFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}
export const parentCategorySuccess = (data) => {
    console.log('data', data)
    return {
        type: actionTypes.GET_PARENT_CATEGORIES,
        parentCategories: data
    };
}

export const authParentCategories = (token) => {
    return dispatch => {
        dispatch(authStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/home/parentCategories',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        }).then(response => {
            console.log('parentCategories', response.data);
            // localStorage.setItem('parentCategories', JSON.stringify(response.data));
           dispatch(parentCategorySuccess(response.data)); 
        }).catch(error => {
            console.log(error);
            dispatch(authFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}
export const authProductsSuccess = (data) => {
    return {
        type: actionTypes.AUTH_PRODUCTS_SUCCESS,
        authProducts: data
    };
}
export const authProducts = () => {
    return dispatch => {
        dispatch(authStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/unAuth/getAllProducts'
        }).then(response => {
            // localStorage.setItem('products', JSON.stringify(response.data));
            dispatch(authProductsSuccess(response.data))
        }).catch(error => {
            dispatch(authFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}
export const authOrdersSuccess = (data) => {
    return {
        type: actionTypes.AUTH_FETCH_ORDERS_SUCCESS,
        authOrders:data
    };
}
export const authOrders = (token) => {
    return dispatch => {
        dispatch(authStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/order/allOrdersByUser',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        }).then(response => {
            console.log(response.data)
            // localStorage.setItem('orders', JSON.stringify(response.data));
            dispatch(authOrdersSuccess(response.data))
        }).catch(error => {
            console.log(error.response.data)
            dispatch(authFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const auth = (email, password) => {
    return dispatch => {
        dispatch(authStart());
        axios.post(`http://localhost:8080/login`, null, {
            params: {
                username: email,
                password: password,
                client_id: 'live-test',
                client_secret: 'abcde'
            }
        }).then(response => {
            const expirationDate = new Date(new Date().getTime() + response.data.expires_in * 1000);
            localStorage.setItem('token', response.data.access_token);
            localStorage.setItem('expirationDate', expirationDate)
            if (response.data.access_token) {
                toast.success('You have been successfully logged In', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(authSuccess(response.data.access_token, email, response.status));
            dispatch(authType(response.data.access_token));
            dispatch(authFirstName(response.data.access_token));
            console.log('hi')
            dispatch(authParentCategories(response.data.access_token))
            console.log('hello')
            dispatch(authProducts())
            dispatch(authOrders(response.data.access_token))
            dispatch(authCheckTimeOut(response.data.expires_in));
        }).catch(error => {
            if (error.response.data.error_description == 'User account is locked') {
                toast.error('Your account has been locked after three successive login failed attempts.', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            else {
                toast.error(error.response.data, {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }

            dispatch(authFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}

export const authLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('expirationDate');
    localStorage.removeItem('type');
    localStorage.removeItem('firstName');
    // localStorage.removeItem('childCategories');
    // localStorage.removeItem('productDetails');
    return {
        type: actionTypes.AUTH_LOGOUT
    };
};

export const authCheckState = () => {//This method is written  so that if the page is refreshed the user still remains loggedIn
    return dispatch => {
        const token = localStorage.getItem('token');
        if (!token) {
            dispatch(authLogout());
        } else {
            const expirationDate = new Date(localStorage.getItem('expirationDate'));//We will get a string it will convert it into a date object
            if (expirationDate <= new Date()) {
                dispatch(authLogout());
            } else {
                dispatch(authSuccess(token));
                dispatch(authCheckTimeOut((expirationDate.getTime() - new Date().getTime()) / 1000));//It will give the time in milisec
            }
        }
    };
};
export const setAuthRedirectPath = (path) => {
    return {
        type: actionTypes.SET_AUTH_REDIRECT_PATH,
        path: path
    };
}

export const signUpCustomerSuccess = (id, email, fname, lname, contact, active, responseStatus) => {
    return {
        type: actionTypes.SIGN_UP_CUSTOMER_SUCCESS,
        id: id,
        email: email,
        firstName: fname,
        lastName: lname,
        contact: contact,
        active: active,
        signUpType: 'customer',
        responseStatus: responseStatus
    };


}
export const signUpCustomer = (firstName, lastName, email, phoneNumber, password, confirmPassword) => {

    return dispatch => {
        dispatch(authStart());
        axios({
            method: 'POST',
            url: 'http://localhost:8080/registration/customer',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: {
                // This is the body part
                email: email,
                firstName: firstName,
                lastName: lastName,
                password: password,
                confirmPassword: confirmPassword,
                active: 'false',
                contact: phoneNumber

            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Your account  have been successfully resgistered. Please activate it by entering the activation token send on your mail', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(signUpCustomerSuccess(response.data.id,
                response.data.email, response.data.firstName, response.data.lastName,
                response.data.contact, response.data.active, response.status))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(authFail(error.response.data.error, error.response.status))
        })
    }


}
export const signUpSellerSuccess = (id, email, fname, lname, gst, companyContact, companyName, active, responseStatus) => {
    return {
        type: actionTypes.SIGN_UP_SELLER_SUCCESS,
        id: id,
        email: email,
        firstName: fname,
        lastName: lname,
        GST: gst,
        companyContact: companyContact,
        companyName: companyName,
        active: active,
        signUpType: 'seller',
        responseStatus: responseStatus
    };


}

export const signUpSeller = (firstName, lastName, companyName, companyContact, email, GST, password, confirmPassword) => {
    return dispatch => {
        dispatch(authStart());
        axios({
            method: 'POST',
            url: 'http://localhost:8080/registration/seller',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: {
                email: email,
                firstName: firstName,
                lastName: lastName,
                password: password,
                confirmPassword: confirmPassword,
                active: 'false',
                gst: GST,
                companyName: companyName,
                companyContact: companyContact,

            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Your account  have been successfully resgistered. Please activate it by entering the activation token send on your mail', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(signUpSellerSuccess(response.data.id,
                response.data.email, response.data.firstName, response.data.lastName,
                response.data.gst, response.data.companyContact, response.data.companyName,
                response.data.active, response.status))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(authFail(error.response.data.error, error.response.status))
        })
    }
}
export const activateAccountSuccess = (responseStatus) => {
    return {
        type: actionTypes.USER_ACTIVATE_SUCCESS,
        responseStatus: responseStatus
    };
}
export const activateAccount = (activationToken) => {
    return dispatch => {
        dispatch(authStart());
        axios.put(`http://localhost:8080/registration/activationLink`, null, {
            params: {
                token: activationToken
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Your account  have been successfully activated', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(activateAccountSuccess(response.status))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(activateAccountSuccess())
            dispatch(authFail(error.response.data.error, error.response.status))
        })
    }

}
export const resendLinkSuccess = (responseStatus) => {
    return {
        type: actionTypes.RESEND_ACTIVATION_LINK_SUCCESS,
        responseStatus: responseStatus

    };
}
export const resendLink = (email) => {
    return dispatch => {
        dispatch(authStart());
        axios.post(`http://localhost:8080/registration/resendActivationLink`, null, {
            params: {
                email: email
            }
        }).then(response => {

            if (response.data != null) {
                toast.success('A link has been successfully send to your entered email', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(resendLinkSuccess(response.status))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(authFail(error.response.data.error, error.response.status))
        })
    }
}
export const forgotPasswordSuccess = (responseStatus) => {
    return {
        type: actionTypes.FORGOT_PASSWORD_LINK_SUCCESS,
        responseStatus: responseStatus

    };
}

export const forgotPassword = (email) => {
    return dispatch => {
        dispatch(authStart());
        axios.post(`http://localhost:8080/forgotPassword/`, null, {
            params: {
                email: email
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('A Password reset token has been successfully send to your registered email', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(forgotPasswordSuccess(response.status))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })

            dispatch(authFail(error.response.data.error, error.response.status))
        })
    }
}



export const forgotPasswordResetSuccess = (responseStatus) => {
    return {
        type: actionTypes.FORGOT_PASSWORD_RESET_SUCCESS,
        responseStatus: responseStatus
    };
}

export const forgotPasswordReset = (password, confirmPassword, token) => {
    return dispatch => {
        dispatch(authStart());
        axios.patch(`http://localhost:8080/forgotPassword/resetPassword`, null, {
            params: {
                password: password,
                confirmPassword: confirmPassword,
                token: token
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Password has been successfully changed', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(forgotPasswordResetSuccess(response.status))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(authFail(error.response.data.error, error.response.status))
        })
    }
}



