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

class UpdatePassword extends Component {
    constructor(props) {
        super(props)

        this.state = {
            password: '',
            confirmPassword: '',
            passwordError: '',
            confirmPasswordError: ''
        }

        this.handleChange = this.handleChange.bind(this)
        this.onSubmitHandler = this.onSubmitHandler.bind(this)
    }
    validate = () => {
        let passwordError = '';
        let confirmPasswordError = '';
        if (!this.state.passwordError) {
            passwordError = "Password cannot be null";
        }
        if (!this.state.confirmPasswordError) {
            confirmPasswordError = "confirm Password cannot be null";
        }

        if (passwordError || confirmPasswordError) {
            this.setState({ passwordError, confirmPasswordError });
            return false;
        }
        return true;
    }
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }
    onSubmitHandler = () => {
        this.setState({ passwordError: '', confirmPasswordError: '' })
        const isValid = this.validate();
        if (isValid) {
            const type = localStorage.getItem('type');
            const token = localStorage.getItem('token');
            this.props.onUpdatePassword(this.state.password, this.state.confirmPassword, token, type);
        }
    }
    render() {
        const { classes } = this.props;
        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <AccountCircleIcon />
                    </Avatar>
                    <Typography
                        className={classes.text}
                        component="h1" variant="h5">
                        Update Password
        </Typography>
                    <form className={classes.form} noValidate>
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
                            id="confirmPassword"
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
                            // onSubmit={(e) => {this.onSubmitHandler(e)}}
                            onClick={this.onSubmitHandler}
                            type="button"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}>
                            Update Password
                      </Button>
                    </form>
                </div>
                <Box mt={8}>
                    <Copyright />
                </Box>
            </Container>
        );
    }
}
const mapDispatchToProps = dispatch => {
    return {
        onUpdatePassword: (password, confirmPassword, token, type) => dispatch(actionCreators.updatePassword(password, confirmPassword, token, type)),
    }
};

export default connect(null, mapDispatchToProps)(withStyles(useStyles)(UpdatePassword));


