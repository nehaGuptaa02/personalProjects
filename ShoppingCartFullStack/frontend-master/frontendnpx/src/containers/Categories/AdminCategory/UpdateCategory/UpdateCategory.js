import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import * as actionCreators from '../../../../store/actions/index';
import Spinner from '../../../../components/UI/Spinner/Spinner';

const Copyright = () => {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            <Link color="inherit" href="/">
                E-CommerceProject
      </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const useStyles = theme => ({
    paper: {
        marginTop: theme.spacing(16),
        marginBottom: theme.spacing(20),
        margin: '20px',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: '#563f46',
    },
    form: {
        width: '100%',
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
        color: 'white',
        backgroundColor: '#563f46',
        '&:hover': {
            background: '#A29599',
        }
    },
    link: {
        color: '#563f46',
    },
    text: {
        color: '#563f46',
    },
    Checkbox: {
        color: '#563f46',
        backgroundColor: '#563f46',
    }
});

class UpdateCategory extends Component {

    constructor(props) {
        super(props)

        this.state = {
            categoryName: '',
            categoryId: '',
            categoryNameError: '',
            categoryIdError: ''
        }
        this.handleChange = this.handleChange.bind(this)
        this.updateCategoryClicked = this.updateCategoryClicked.bind(this)
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
        let categoryNameError = '';
        let categoryIdError = '';

        if (!this.state.categoryName) {
            categoryNameError = "Category Name cannot be blank";
        }
        if (!this.state.categoryId) {
            categoryIdError = "Category Id cannot be blank";
        }
        if (categoryNameError || categoryIdError) {
            this.setState({
                categoryNameError, categoryIdError
            });
            return false;
        }

        return true;
    };
    updateCategoryClicked = (e) => {
        e.preventDefault();
        this.setState({
            categoryIdError: '', categoryNameError:''
        })
        const isValid = this.validate();
        if (isValid) {
            const token = localStorage.getItem('token');
            console.log();
            this.props.onUpdateCategory(this.state.categoryName, this.state.categoryId, token)
        }
    }

    render() {
        const { classes } = this.props;
        let form = (<Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>

                <Typography
                    className={classes.text}
                    component="h1" variant="h5">
                    Update Category</Typography>
                <form className={classes.form} noValidate>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        fullWidth
                        required
                        name="categoryId"
                        label="Category Id"
                        id="categoryId"
                        onChange={this.handleChange}
                    />
                    <div style={{ fontSize: 12, color: '#563f46' }}>
                        {this.state.categoryIdError}
                    </div>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="categoryName"
                        label="Category Name"
                        name="categoryName"
                        autoFocus
                        onChange={this.handleChange}
                    />  <div style={{ fontSize: 12, color: '#563f46' }}>
                        {this.state.categoryNameError}
                    </div>

                    <Button
                        onClick={(e) => this.updateCategoryClicked(e)}
                        type="button"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                    >Update Category</Button>
                </form>
            </div>
            <Box mt={8}>
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
        loading: state.category.loading
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onUpdateCategory: (name, id, token) => dispatch(actionCreators.updateCategory(name, id, token))
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(UpdateCategory));


