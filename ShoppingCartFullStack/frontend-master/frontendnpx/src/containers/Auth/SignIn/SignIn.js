import React, { Component } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import * as actionCreators from '../../../store/actions/index';
import Spinner from '../../../components/UI/Spinner/Spinner';
import { toast } from 'react-toastify';
import { updatedObject } from '../../../shared/utility';
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
        marginTop: theme.spacing(7),
        marginBottom: theme.spacing(11),
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

class SignIn extends Component {

    constructor(props) {
        super(props)

        this.state = {
            email: '',
            password: '',
            emailError: '',
            passwordError: ''
        }

        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }
    validate = () => {
        let emailError = '';
        let passwordError = '';

        if (!this.state.password) {
            passwordError = "Password cannot be blank";
        }

        if (!this.state.email.includes("@")) {
            emailError = "Invalid email";
        }

        if (emailError || passwordError) {
            this.setState({ emailError, passwordError });

            return false;
        }

        return true;
    };
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }
    loginClicked = (e) => {
        e.preventDefault();
        this.setState({ emailError: '', passwordError: '' })
        const isValid = this.validate();
        if (isValid) {
            this.props.onAuth(this.state.email, this.state.password)
        }
        if (this.props.responseStatus == 200) {
            this.props.history.push('/');

        }



    }



    render() {
        const { classes } = this.props;
        let form = (
        <Container component="main" maxWidth="xs">
            {this.props.responseStatus == 200 ? this.props.history.push('/'):null }
            <CssBaseline />
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography
                    className={classes.text}
                    component="h1" variant="h5">
                    Sign in
</Typography>
                <form className={classes.form} noValidate>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus
                        onChange={this.handleChange}
                    />
                    <div style={{ fontSize: 12, color: '#563f46' }}>
                        {this.state.emailError}
                    </div>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                        onChange={this.handleChange}
                    />
                    <div style={{ fontSize: 12, color: '#563f46', }}>
                        {this.state.passwordError}
                    </div>
                    <FormControlLabel
                        control={<Checkbox className={classes.text} value="remember" color="primary" />}
                        label="Remember me"
                    />
                    <Button
                        onClick={(e) => this.loginClicked(e)}
                        type="button"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                    >
                        Sign In
  </Button>
                    <Grid container>
                        <Grid item xs>
                            <Link className={classes.link} href="/forgotPasswordLink" variant="body2">
                                Forgot password?
      </Link>
                        </Grid>
                        <Grid item>
                            <Link className={classes.link} href="/signup" variant="body2">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
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
        loading: state.auth.loading,
        error: state.auth.error,
        isAuthenticated: state.auth.token !== null,
        authRedirectPath: state.auth.authRedirectPath,
        token: state.auth.token,
        errorStatus: state.auth.errorStatus,
        responseStatus: state.auth.responseStatus
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onAuth: (email, password) => dispatch(actionCreators.auth(email, password)),//We can set here isSignUp also
        onSetAuthRedirectPath: (path) => dispatch(actionCreators.setAuthRedirectPath(path))
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(SignIn));


