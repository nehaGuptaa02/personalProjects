import * as actionTypes from './actionTypes';
import axios from 'axios';
import { toast } from 'react-toastify';
export const customerStart = () => {
    return {
        type: actionTypes.CUSTOMER_START
    };
}

export const customerFail = (error, errorStatus) => {
    return {
        type: actionTypes.CUSTOMER_FAIL,
        error: error,
        errorStatus: errorStatus
    };
}
export const viewProfileSuccess = (data) => {
    return {
        type: actionTypes.CUSTOMER_PROFILE_SUCCESS,
        id: data.id,
        firstName: data.firstName,
        lastName: data.lastName,
        isActive: data.active,
        contact: data.contact,
        imagePath: data.filePath,
        email: data.email

    };
}
export const viewCustomerProfile = (token) => {
    return dispatch => {
        dispatch(customerStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/customer/profile',
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then(response => {
            console.log('view customer profile', response.data);
            dispatch(viewProfileSuccess(response.data));
        }).catch(error => {
            alert(error.response.data)
            dispatch(customerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}
export const viewAddressesSuccess = (data) => {
    return {
        type: actionTypes.CUSTOMER_ADDRESSES_SUCCESS,
        addresses: data
    };
}
export const viewCustomerAddresses = (token) => {
    return dispatch => {
        dispatch(customerStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/customer/addresses',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            dispatch(viewAddressesSuccess(response.data));
        }).catch(error => {
            alert(error.response.data)
            dispatch(customerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}
export const addAddressSuccess = () => {
    return {
        type: actionTypes.ADD_ADDRESS_SUCCESS
    };
}

export const addAddress = (city, state, country, zipCode, label, addressLine, token, type) => {
    return dispatch => {
        let typeurl = 'http://localhost:8080/customer/addAddress'
        if (type === 'seller') {
            typeurl = 'http://localhost:8080/seller/addAddress'
        }

        dispatch(customerStart());
        axios({
            method: 'POST',
            url: typeurl,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            data: {
                'city': city,
                'state': state,
                'country': country,
                'zipCode': zipCode,
                'label': label,
                'addressLine': addressLine
            }
        }).then(response => {
            // alert(response.data)
            if (response.data != null) {
                toast.success('Address has been successfully added', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(addAddressSuccess(response.data));
        }).catch(error => {
            // alert(error.response.data)
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(customerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}
export const deleteAddressSuccess = () => {
    return {
        type: actionTypes.DELETE_ADDRESS_SUCCESS
    };
}
export const deleteAddress = (addressId, token) => {
    return dispatch => {
        dispatch(customerStart());
        axios({
            method: 'DELETE',
            url: `http://localhost:8080/customer/deleteAddress/${addressId}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.data != null) {
                toast.success(response.data, {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(viewCustomerAddresses(localStorage.getItem('token')))
            dispatch(deleteAddressSuccess(response.data));
        }).catch(error => {
            // alert(error.response.data)
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(customerFail(error.response.data.error, error.response.status))

        })
    };
}
export const updatePasswordSuccess = () => {
    return {
        type: actionTypes.UPDATE_PASSWORD_SUCCESS
    };
}
export const updatePassword = (password, confirmPassword, token, type) => {
    let typeurl = 'http://localhost:8080/customer/updatePassword'
    if (type === 'seller') {
        typeurl = 'http://localhost:8080/seller/updatePassword'
    }
    return dispatch => {
        dispatch(customerStart());
        axios({
            method: 'PATCH',
            url: `${typeurl}?password=${password}&confirmPassword=${confirmPassword}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Password reset successfull', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(updatePasswordSuccess(response.data));
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(customerFail(error.response.data.error, error.response.status))

        })
    };
}


export const updateAddressSuccess = () => {
    return {
        type: actionTypes.UPDATE_ADDRESS_SUCCESS
    };
}

export const updateAddress = (addressId, city, state, country, zipCode, label, addressLine, token, type) => {
    return dispatch => {
        let typeUrl = `http://localhost:8080/customer/updateAddress/${addressId}`
        if (type === 'seller') {
            typeUrl = `http://localhost:8080/seller/updateAddress/${addressId}`
        }
        dispatch(customerStart());
        axios({
            method: 'PATCH',
            url: typeUrl,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            data: {
                'city': city,
                'state': state,
                'country': country,
                'zipCode': zipCode,
                'label': label,
                'addressLine': addressLine
            }
        }).then(response => {
            toast.success('Address has been successfully updated', {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(updateAddressSuccess(response.data));
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(customerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}

export const updateCustomerProfileSuccess = () => {
    return {
        type: actionTypes.UPDATE_CUSTOMER_PROFILE_SUCCESS
    };
}

export const updateCustomerProfile = (firstName, lastName, email, phoneNumber, token) => {
    return dispatch => {
        dispatch(customerStart());
        axios({
            method: 'PATCH',
            url: 'http://localhost:8080/customer/updateProfile',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            data: {
                'firstName': firstName,
                'lastName': lastName,
                'email': email,
                'phoneNumber': phoneNumber
            }
        }).then(response => {
            toast.success('Profile has been successfully update', {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            // dispatch();
            dispatch(viewCustomerProfile(localStorage.getItem('token')))
            dispatch(updateCustomerProfileSuccess(response.data));
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(customerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const sellerStart = () => {
    return {
        type: actionTypes.SELLER_START
    };
}

export const sellerFail = (error, errorStatus) => {
    return {
        type: actionTypes.SELLER_FAIL,
        error: error,
        errorStatus: errorStatus
    };
}
export const sellerAddressSuccess = (data) => {
    return {
        type: actionTypes.SELLER_ADDRESS_SUCCESS,
        sellerAddress: data
    };
}
export const getSellerAddress = (token) => {
    return dispatch => {
        dispatch(sellerStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/seller/address',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            dispatch(sellerAddressSuccess(response.data));
        }).catch(error => {
            alert(error.response.data)
            dispatch(sellerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}
export const viewSellerProfileSuccess = (data) => {
    return {
        type: actionTypes.SELLER_PROFILE_SUCCESS,
        id: data.id,
        firstName: data.firstName,
        lastName: data.lastName,
        contact: data.companyContact,
        companyName: data.companyName,
        gst: data.gst,
        imagePath: data.filePath,
        email: data.email

    };
}

export const viewSellerProfile = (token) => {
    return dispatch => {
        dispatch(sellerStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/seller/profile',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            const token = localStorage.getItem('token');
            dispatch(getSellerAddress(token))
            dispatch(viewSellerProfileSuccess(response.data));
        }).catch(error => {
            alert(error.response.data)
            dispatch(sellerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}

export const updateSellerProfileSuccess = () => {
    return {
        type: actionTypes.SELLER_PROFILE_SUCCESS
    };
}
export const updateSellerProfile = (firstName, lastName, email, phoneNumber, companyContact, gst, token) => {
    return dispatch => {
        dispatch(sellerStart());
        axios({
            method: 'PATCH',
            url: 'http://localhost:8080/seller/updateProfile',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            data: {
                'firstName': firstName,
                'lastName': lastName,
                'email': email,
                'phoneNumber': phoneNumber,
                'companyName': companyContact,
                'gst': gst
            }
        }).then(response => {
            if (response.status == 200 || response.status == 201) {
                toast.success('Profile has been successfully update', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }

            dispatch(updateSellerProfileSuccess(response.data));
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(sellerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const imageUploadSuccess = () => {
    return {
        type: actionTypes.IMAGE_UPLOAD_SUCCESS
    };
}
export const imageUpload = (imageUrl, token) => {
    return dispatch => {
        dispatch(customerStart());
        axios({
            method: 'POST',
            url: `http://localhost:8080/image/setProfileImagePath?imageUrl=${imageUrl}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Your profile picture has been successfully uploaded', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            let type = localStorage.getItem('type');
            if (type === 'customer') {
                dispatch(viewCustomerProfile(localStorage.getItem('token')))

            }
            if (type === 'seller') {
                dispatch(viewSellerProfile(localStorage.getItem('token')))
            }
            dispatch(imageUploadSuccess());
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(customerFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}


