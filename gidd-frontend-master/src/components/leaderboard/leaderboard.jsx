import { useState, useEffect } from "react";
import { UserService } from "../../data/Services/apiUserService";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import IconButton from "@material-ui/core/IconButton";
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import ArrowDownward from "@material-ui/icons/ArrowDownward";
import Container from "@material-ui/core/Container";
import Alert from "@material-ui/lab/Alert";

function Leaderboard() {
  const [leaderboardEntries, setLeaderboardEntries] = useState([]);
  const [ascendingPoints, setAscendingPoints] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    UserService.getTopFiftyUsers().then((response) => {
      if (!response) {
        setError(true);
        return;
      }

      const entries = response.data.payload;

      // Apply rank
      let i = 0;
      entries.forEach((entry, index) => {
        entries[index].rank = ++i;
      });

      setLeaderboardEntries(entries);
    });
  }, []);

  const toggleColumnSortByPoints = () => {
    setLeaderboardEntries((leaderboardEntries) => {
      return leaderboardEntries.reverse();
    });
    setAscendingPoints((ascending) => {
      return !ascending;
    });
  };

  const renderError = () => {
    return (
      <Container maxWidth="md">
        <Alert severity="error">
          Det oppstod en feil under innlasting av poengtavlen.
        </Alert>
      </Container>
    );
  };

  const renderLeaderboard = () => {
    return (
      <Container maxWidth="md">
        <h1>Poengtavle</h1>
        <TableContainer className="leaderboardTable" component={Paper}>
          <Table className="leaderboardTable">
            <TableHead>
              <TableRow>
                <TableCell>Rank</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>
                  Points &nbsp;
                  <IconButton
                    color="primary"
                    onClick={toggleColumnSortByPoints}
                  >
                    {ascendingPoints ? <ArrowUpward /> : <ArrowDownward />}
                  </IconButton>
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {leaderboardEntries.map((entry) => (
                <TableRow key={entry.rank}>
                  <TableCell component="th" scope="row">
                    {entry.rank}
                  </TableCell>
                  <TableCell>
                    {entry.firstName} {entry.surName}
                  </TableCell>
                  <TableCell>{entry.points}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Container>
    );
  };

  if (error) {
    return renderError();
  }

  return renderLeaderboard();
}

export default Leaderboard;
