import * as actionTypes from './actionTypes';
import axios from 'axios';
import { toast } from 'react-toastify';
export const categoryStart = () => {
    return {
        type: actionTypes.CATEGORY_START
    };
}

export const categoryFail = (error, errorStatus) => {
    return {
        type: actionTypes.CATEGORY_FAIL,
        error: error,
        errorStatus: errorStatus
    };
}
export const categoryAddMetadataSuccess = () => {
    return {
        type: actionTypes.CATEGORY_ADD_METADATA_FIELD_SUCCESS,
    };
}
export const addMetadataField = (fieldName, token) => {
    return dispatch => {
        dispatch(categoryStart());
        axios({
            method: 'POST',
            url: `http://localhost:8080/admin/addMetadataField?fieldName=${fieldName}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then(response => {
            if (response.data != null) {
                toast.success('Metadata Field has been successfully added', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            // alert('Metadata field has been added successfully');
            dispatch(categoryAddMetadataSuccess());
        }).catch(error => {
            // alert(error.response.data)
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(categoryFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}
export const categoryViewAllMetadataSuccess = (data) => {
    return {
        type: actionTypes.CATEGORY_VIEW_ALL_METADATA_FIELDS_SUCCESS,
        allMetadataFields: data
    };
}
export const viewAllMetadataFields = (token) => {
    return dispatch => {
        dispatch(categoryStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/admin/viewAllMetadataFields',
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then(response => {
            dispatch(categoryViewAllMetadataSuccess(response.data));
        }).catch(error => {
            alert(error.response.data);
            dispatch(categoryFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}

export const addCategorySuccess = () => {
    return {
        type: actionTypes.ADD_ADDRESS_SUCCESS,
    };
}
export const addCategory = (name, id, token) => {
    console.log('request')

    return dispatch => {
        dispatch(categoryStart());
        axios({
            method: 'POST',
            url: `http://localhost:8080/admin/addCategory?categoryName=${name}&parentId=${id}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.status === 201) {
                toast.success('Metadata Field has been successfully added', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(addCategorySuccess());
        }).catch(error => {
            toast.error(error.response.data.message, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(categoryFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}
export const viewCategorySuccess = (data) => {
    return {
        type: actionTypes.VIEW_CATEGORY_SUCCESS,
        categoryDto: data.data.categoryDto,
        subCategories: data.data.subCategories,
        categoryMetadataFieldDtoSet: data.data.categoryMetadataFieldDtoSet
    };
}
export const viewCategory = (id, token) => {
    return dispatch => {
        dispatch(categoryStart());
        axios({
            method: 'GET',
            url: `http://localhost:8080/home/uptoAllChildCategories?id=${id}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            dispatch(viewCategorySuccess(response.data));
        }).catch(error => {
            alert(error.response.data.message);
            dispatch(categoryFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}
export const viewAllCategoriesSuccess = (data) => {
    return {
        type: actionTypes.VIEW_ALL_CATEGORIES_SUCCESS,
        allCategories: data.data
    };
}
export const viewAllCategories = (token) => {
    const type = localStorage.getItem('type');
    let typeUrl = 'http://localhost:8080/admin/categories';
    if (type === 'seller') {
        typeUrl = 'http://localhost:8080/seller/categories';
    }
    return dispatch => {
        dispatch(categoryStart());
        axios({
            method: 'GET',
            url: typeUrl,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            dispatch(viewAllCategoriesSuccess(response.data));
        }).catch(error => {
            alert(error.response.data.message);
            dispatch(categoryFail(error.response.data.error, error.response.status))
        })
    };
}


export const updateCategorySuccess = (data) => {
    return {
        type: actionTypes.UPDATE_CATEGORY_SUCCESS,
    };
}
export const updateCategory = (name, id, token) => {
    return dispatch => {
        dispatch(categoryStart());
        axios({
            method: 'PUT',
            url: `http://localhost:8080/admin/updateCategory?id=${id}&name=${name}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.status === 200)

                toast.success('Category  has been successfully added', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            dispatch(updateCategorySuccess(response.data));
        }).catch(error => {
            toast.error(error.response.data.message, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(categoryFail(error.response.data.error, error.response.status))
        })
    };
}