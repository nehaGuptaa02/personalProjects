import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './ViewAllCategories.module.css';
import * as actionCreators from '../../../../store/actions/index';
// import { Pagination } from 'react-bootstrap';
class ViewAllCategories extends Component {
    componentDidMount() {
        const token = localStorage.getItem('token');
        this.props.onViewAllCategories(token);
    }
    render() {

        let categoryList = null;
        if (this.props.allCategories) {
            categoryList = this.props.allCategories.map(category => (
                <div key={category.categoryDto.id} className={styles.Div}>
                    <p>ID : {category.categoryDto.id} <br></br></p>

                    <p>CATEGORY NAME: {category.categoryDto.name}<br></br> </p>

                    {category.subCategories.map(sub => (
                        <div key={sub.id} className={styles.Sub}>
                            <p>SUBCATEGORY ID: {sub.id}</p>
                            <p>SUBCATEGORY NAME: {sub.name}</p>
                        </div>
                    ))}
                    {/* {() => this.props.subCategory(category.subCategories)} */}
                </div>
            ))
            return (
                <div>
                    <strong><h1 style={{ color: '#563f46', textAlign: 'center' }}>
                        All Categories </h1></strong>
                    {categoryList}
                </div>
            );
        }

    }
}
const mapStateToProps = state => {
    return {
        allCategories: state.category.allCategories
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onViewAllCategories: (token) => dispatch(actionCreators.viewAllCategories(token))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ViewAllCategories);