import React, { Component } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import { connect } from 'react-redux';
import Spinner from '../../../components/UI/Spinner/Spinner';
import * as actionCreators from '../../../store/actions/index';
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
        marginTop: theme.spacing(9),
        marginBottom: theme.spacing(14),
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
        marginTop: theme.spacing(6),
        marginBottom: theme.spacing(8),
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

class ForgotPasswordReset extends Component {
    constructor(props) {
        super(props)

        this.state = {
            token: '',
            password: '',
            confirmPassword: '',
            tokenError: '',
            passwordError: '',
            confirmPasswordError: ''
        }

        this.handleChange = this.handleChange.bind(this)
        this.onSubmitHandler = this.onSubmitHandler.bind(this)
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
        let tokenError = '';
        let passwordError = '';
        let confirmPasswordError = '';

        if (!this.state.token) {
            tokenError = "Token cannot be null";
        }
        if (!this.state.passwordError) {
            passwordError = "Password cannot be null";
        }
        if (!this.state.confirmPasswordError) {
            confirmPasswordError = "confirm Password cannot be null";
        }

        if (tokenError || passwordError || confirmPasswordError) {
            this.setState({ tokenError, passwordError, confirmPasswordError });
            return false;
        }
        return true;
    }
    onSubmitHandler = (e) => {
        e.preventDefault();
        this.setState({ tokenError: '', passwordError: '', confirmPasswordError: '' })
        const isValid = this.validate();
        if (isValid) {
            this.props.onForgotPasswordReset(this.state.password, this.state.confirmPassword, this.state.token)
        }
    }
    render() {
        const { classes } = this.props;
        let form = (
            <Container component="main" maxWidth="xs">
                {this.props.responseStatus == 200 ? this.props.history.push('/activateAccount') : null}
                <CssBaseline />
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <AccountCircleIcon />
                    </Avatar>
                    <Typography
                        className={classes.text}
                        component="h1" variant="h5">
                        Please enter the token sent to your registered email.
        </Typography>
                    <form className={classes.form} noValidate>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="token"
                            label="Reset Password Token"
                            name="token"
                            autoFocus
                            onChange={this.handleChange}
                        />
                        <div style={{ fontSize: 12, color: '#563f46' }}>
                            {this.state.tokenError}
                        </div>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="password"
                            label="Password"
                            type="password"
                            name="password"
                            autoFocus
                            onChange={this.handleChange}
                        />
                        <div style={{ fontSize: 12, color: '#563f46' }}>
                            {this.state.passwordError}
                        </div>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="password"
                            type="password"
                            label="Confirm Password"
                            name="confirmPassword"
                            autoFocus
                            onChange={this.handleChange}
                        />

                        <div style={{ fontSize: 12, color: '#563f46' }}>
                            {this.state.confirmPasswordError}
                        </div>

                        <Button
                            onClick={(e)=>this.onSubmitHandler(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}>
                            Reset Password
                      </Button>
                        <Grid container>
                            <Grid item>
                                <Link className={classes.link} href="/" variant="body2">
                                    {"CustomerService"}
                                </Link>
                            </Grid>
                        </Grid>
                    </form>
                </div>
                <Box mt={8}>
                    <Copyright />
                </Box>
            </Container>
        );
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
        loading: state.auth.loading,
        responseStatus:state.auth.responseStatus
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onForgotPasswordReset: (password, confirmPassword, token) => dispatch(actionCreators.forgotPasswordReset(password, confirmPassword, token))

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(ForgotPasswordReset));


