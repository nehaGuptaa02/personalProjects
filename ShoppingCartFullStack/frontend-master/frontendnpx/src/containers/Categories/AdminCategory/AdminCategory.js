import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import { withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import styles from './AdminCategory.module.css';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import * as actionCreators from '../../../store/actions/index';
const useStyles = theme => ({
    button: {
        position: 'relative',
        backgroundColor: '#563f46',
        color: 'white',
        textAlign: 'center',
        height: '70px',
        width: '180px',
        margin: '30px',
        fontSize: '10px',
        display: 'inline-block',
        '&:hover': {
            background: '#A29599',
        }
    },
    typography: {
        padding: '25px',
        color: '#563f46'

    },
    cancel: {
        margin: '20px 50%',
        color: 'black',
        backgroundColor: '#A29599'

    },
    bottom:{
        margin: '40px 0px',
    }
});

class AdminCategory extends Component {
    addMetadataFieldHandler = () => {
        this.props.history.push('/addMetadataField');

    }
    viewAllMetadataFieldsHandler = () => {
        const token = localStorage.getItem('token');
        this.props.onViewAllMetadataFields(token);
        if (this.props.allMetadataFields != null) {
            this.props.history.push('viewAllMetadataFields');
        }
    }
    addCategoryHandler = () => {
        this.props.history.push('/addCategory');
    }
    viewCategoryHandler = () => {
        this.props.history.push('/viewCategory');
    }
    viewAllcategoriesHandler = () => {
        const token = localStorage.getItem('token');
        this.props.onViewAllCategories(token);
        if (this.props.allCategories != null) {
            this.props.history.push('viewAllCategories');
        }
    }
    updateCategoryHandler = () => {
        this.props.history.push('/updateCategory');
    }
    addMetadataValuesHandler=()=>{
        this.props.history.push('/addMetadataValues');
    }
    updateMetadataValuesHandler=()=>{
        this.props.history.push('/');
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
                    Welcome Admin in the Categories !!
                </Typography>
                <Button
                    onClick={this.addMetadataFieldHandler}
                    className={classes.button}
                    variant="contained" >Add a Metadata Field</Button>
                <Button
                    onClick={this.viewAllMetadataFieldsHandler}
                    className={classes.button}
                    variant="contained" >View All Metadata Fields</Button>
                <Button
                    onClick={this.addCategoryHandler}
                    className={classes.button}
                    variant="contained" >Add a category</Button>
                <Button
                    onClick={this.viewCategoryHandler}
                    className={classes.button}
                    variant="contained" >View a category</Button>
                <Button
                    onClick={this.viewAllcategoriesHandler}
                    className={classes.button}
                    variant="contained" >View All Categories</Button>
                {/* <Button
                    onClick={this.activateDeactivateCustomerHandler}
                    className={classes.button}
                    variant="contained" >Delete a category</Button> */}
                <div className={classes.bottom}>
                    <Button
                        onClick={this.updateCategoryHandler}
                        className={classes.button}
                        variant="contained" >Update a category</Button>
                    <Button
                        onClick={this.addMetadataValuesHandler}
                        className={classes.button}
                        variant="contained" >Add new category Metadata Field for a category</Button>
                    <Button
                        onClick={this.updateMetadataValuesHandler}
                        className={classes.button}
                        variant="contained" >Update Values for existing metadata Field in a category </Button>
                </div>
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
        allMetadataFields: state.category.allMetadataFields,
        allCategories: state.category.allCategories
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onViewAllMetadataFields: (token) => dispatch(actionCreators.viewAllMetadataFields(token)),
        onViewAllCategories: (token) => dispatch(actionCreators.viewAllCategories(token))
    };
}
export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(AdminCategory));