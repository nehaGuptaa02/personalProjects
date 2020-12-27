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
import * as actionCreators from '../../store/actions/index';
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
        marginTop: '20px',
        marginBottom: 'auto',
    },
    Checkbox: {
        color: '#563f46',
        backgroundColor: '#563f46',
    }
});

class ActivationLink extends Component {
    constructor(props) {
        super(props)

        this.state = {
            activationToken: '',
            activationTokenError: ''

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
        let activationTokenError = '';

        if (!this.state.activationToken) {
            activationTokenError = "Activation Token cannot be blank";
        }

        if (activationTokenError) {
            this.setState({ activationTokenError });
            return false;
        }
        return true;
    }



    onSubmitHandler = (e) => {
        e.preventDefault();
        this.setState({activationTokenError:''})
        const isValid = this.validate();
        if (isValid) {
            this.props.onActivation(this.state.activationToken)
            // this.props.history.push('/');
        }

    }
    render() {
        const { classes } = this.props;
        return (
            <Container component="main" maxWidth="xs">
            {this.props.responseStatus == 200 ? this.props.history.push('/signIn'):null}
                <CssBaseline />
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <AccountCircleIcon />
                    </Avatar>
                    <Typography
                        className={classes.text}
                        component="h1" variant="h5">

                        Please enter the activation token sent on your mail while registering it.
        </Typography>
                    <form className={classes.form} noValidate>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="activationToken"
                            label="Activation Token"
                            name="activationToken"
                            autoFocus
                            onChange={this.handleChange}
                        />
                        <div style={{ fontSize: 12, color: '#563f46' }}>
                            {this.state.activationTokenError}
                        </div>

                        <Button
                            onClick={(e) => this.onSubmitHandler(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}
                        >
                            Activate
                      </Button>
                        <Grid container>
                            <Grid item xs>
                                <Link className={classes.link} href="/resendActivationLink" variant="body2">
                                    Resend Activation Link?
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
            </Container>
        );
    }
}
const mapStateToProps = state => {
    return {
        loading: state.auth.loading,
        error: state.auth.error,
        errorStatus: state.auth.errorStatus,
        responseStatus: state.auth.responseStatus
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onActivation: (activationToken) => dispatch(actionCreators.activateAccount(activationToken))

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(ActivationLink));
// export default withStyles(useStyles)(ActivationLink);

