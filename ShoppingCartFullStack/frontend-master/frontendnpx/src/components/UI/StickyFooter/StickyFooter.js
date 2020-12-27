import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Link from '@material-ui/core/Link';

const Copyright = () => {
  return (
    <Typography variant="body2" color="inherit">
      {'Copyright © '}
      <Link color="inherit" href="http://localhost:3000/">
        E-CommerceProject
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    flexDirection: 'column',//To streact it at its maximum width
  },
  footer: {
    padding: theme.spacing(3, 2),//By default it is a multiple of 2
    marginTop: 'auto',
    backgroundColor:'#563f46',
    color:'white',
    position:'sticky'
      // theme.palette.type === 'light' ? theme.palette.grey[200] : theme.palette.grey[800],
  },
}));

export default function StickyFooter() {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      <CssBaseline />
      <footer className={classes.footer}>
        <Container maxWidth="sm">
          <Typography variant="body1">Here I will apply internationalizations for countries with their links</Typography>
          <Copyright />
        </Container>
      </footer>
    </div>
  );
}
