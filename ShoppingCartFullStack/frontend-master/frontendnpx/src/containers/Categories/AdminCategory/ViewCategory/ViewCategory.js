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
        marginTop: theme.spacing(21),
        marginBottom: theme.spacing(25),
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

class ViewCategory extends Component {

    constructor(props) {
        super(props)

        this.state = {
            categoryId: '',
            categoryIdError: ''
        }

        this.handleChange = this.handleChange.bind(this)
        this.viewCategoryClicked = this.viewCategoryClicked.bind(this)
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
        let categoryIdError = '';
        if (!this.state.categoryId) {
            categoryIdError = "Category Id cannot be blank";
        }
        if (categoryIdError) {
            this.setState({
                categoryIdError
            });
            return false;
        }

        return true;
    };
    viewCategoryClicked = (e) => {
        e.preventDefault();
        this.setState({
            categoryIdError: ''
        })
        const isValid = this.validate();
        if (isValid) {
            const token = localStorage.getItem('token');
            this.props.onViewCategory(this.state.categoryId, token)
            if (this.props.error === null || this.props.error === false) {
                this.props.history.push('/detailsOfCategory');
            }
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
                    View Category</Typography>
                <form className={classes.form} noValidate>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        name="categoryId"
                        label="Category Id"
                        id="categoryId"
                        onChange={this.handleChange}
                    />
                    <div style={{ fontSize: 12, color: '#563f46' }}>
                        {this.state.categoryIdError}
                    </div>
                    <Button
                        onClick={(e) => this.viewCategoryClicked(e)}
                        type="button"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                    >View Category</Button>
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
        loading: state.category.loading,
        error: state.category.error
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onViewCategory: (id, token) => dispatch(actionCreators.viewCategory(id, token))
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(ViewCategory));


