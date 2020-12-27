import React, { Component } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import * as actionCreators from '../../../../store/actions/index';
import Spinner from '../../../../components/UI/Spinner/Spinner';
import { updateObject } from '../../../../shared/utility';
import TextareaAutosize from '@material-ui/core/TextareaAutosize';
const useStyles = theme => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: '#563f46',
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
        color: 'white',
        backgroundColor: '#563f46',
        '&:hover': {
            background: '#A29599',
        }
    },
    logInLink: {
        color: '#563f46',
    },
    notchedOutline: {
        borderWidth: "10px",
        borderColor: "#563f46"
    }
});
const Copyright = () => {
    return (
        <Typography variant="body2" color="inherit" align="center">
            {'Copyright © '}
            <Link color="inherit" href="/">
                E-CommerceProject
      </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

class AddProduct extends Component {

    constructor(props) {
        super(props)

        this.state = {
            name: '',
            brand: '',
            categoryId: '',
            nameError: '',
            brandError: '',
            categoryIdError: '',
            description: '',
            isCancellable: '',
            isReturnable: ''


        }

        this.handleChange = this.handleChange.bind(this)
        this.addProductClicked = this.addProductClicked.bind(this)
    }
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    validate = () => {
        let nameError = '';
        let brandError = '';
        let categoryIdError = '';
        if (!this.state.name) {
            nameError = "Name of the product cannot be blank";
        }
        // if (!this.state.brandName) {
        //     brandError = "Brand Name cannot be blank";
        // }

        if (!this.state.categoryId) {
            categoryIdError = "Category Id cannot be blank.";
        }
        if (nameError || brandError || categoryIdError) {
            this.setState({
                nameError, brandError, categoryIdError
            });
            return false;
        }

        return true;
    };
    addProductClicked = (e) => {
        e.preventDefault();
        this.setState({
            nameError: '', brandError: '', categoryIdError: ''
        })
        const isValid = this.validate();
        if (isValid) {
            const token = localStorage.getItem('token');
            this.props.onAddProduct(this.state.name, this.state.brand, this.state.categoryId,
                this.props.description, this.props.isCancellable, this.props.isReturnable, token);
        }


    }
    render() {
        const { classes } = this.props;
        let form = (
            <Container component="main" maxWidth="xs">
            {this.props.responseStatus == 200 ? this.props.history.push('/sellerProduct'):null}
                <CssBaseline />
                <div className={classes.paper}>
                    <Typography component="h1" variant="h5">
                        Add Product
    </Typography>
                    <form className={classes.form} noValidate>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <TextField

                                    classes={{ root: classes.notchedOutline }}
                                    onChange={this.handleChange}
                                    name="name"
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="name"
                                    label="Product Name"
                                    autoFocus
                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.nameError}
                                </div>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="brand"
                                    label="Brand Name"
                                    name="brand"
                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.brandError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="categoryId"
                                    label="Category Id"
                                    name="categoryId"

                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.categoryIdError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    fullWidth
                                    id="description"
                                    label="Description"
                                    name="description"
                                />
                            
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    fullWidth
                                    name="isCancellable"
                                    label="Is Cancellable"
                                    id="isCancellable"

                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    fullWidth
                                    name="isReturnable"
                                    label="IsReturnable"
                                    id="isReturnable"
                                />
                            </Grid>
                        </Grid>
                        <Button
                            className={classes.submit}
                            onClick={(e) => this.addProductClicked(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                        > Add Product
                   </Button>
                    </form>
                </div>
                <Box mt={5}>
                    <Copyright />
                </Box>
            </Container>);
        if (this.props.loading) {
            form = <Spinner />
        }

        return (
            <div>
                {form}
            </div>

        );


    }
}
const mapStateToProps = state => {
    return {
        loading: state.product.loading,
         responseStatus: state.auth.responseStatus
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onAddProduct: (name, brand, categoryId, description, isCancellable, isReturnable, token) => dispatch(actionCreators.addProduct(name, brand, categoryId, description, isCancellable, isReturnable, token))
    };
}


export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(AddProduct))