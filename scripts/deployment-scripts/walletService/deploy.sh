#!/bin/bash

COMMAND=$1
PID_FILE=RUNNING_PID

APP_NAME=walletimpl
DAEMON=bin/$APP_NAME
function stopServer() {
  echo "Stopping $APP_NAME ..."
  if [ -e $PID_FILE ]
  then
    kill -9 `cat $PID_FILE`
    rm -f $PID_FILE
  else
      echo "$APP_NAME is not running"
  fi
}

function startServer() {
        local TMP_LOG="$APP_NAME.log"
        if [ -e $PID_FILE ]
        then
                echo "PID File exists at $PID_FILE"
                pid=`cat $PID_FILE`
                if ps -p $pid > /dev/null
                then
                        echo "And server is already running with PID: $pid"
                        exit 1
                else
                        rm -f $PID_FILE
                fi
        fi
        echo "Starting $APP_NAME daemon $DAEMON logging output to $TMP_LOG"
        nohup $DAEMON >$TMP_LOG  -Dlagom.cluster.join-self=on -Dconfig.file=/opt/livelygig/wallet_impl_prod.conf 2>&1 &

}

function checkStatus() {
        if [ -e $PID_FILE ]
        then
                pid=`cat $PID_FILE`
                if ps -p $pid > /dev/null
                then
                        echo "$APP_NAME is running with PID: $pid"
                        exit 0
                fi
        fi
        echo "$APP_NAME is not running"
}

case "${COMMAND}" in
  start)
        startServer
        ;;
  restart)
        echo "Restarting $APP_NAME"
        stopServer
        startServer
        ;;
  stop)
        stopServer
        ;;
  status)
        checkStatus
        ;;
  *)
        echo "Usage: $APP_NAME {start|restart|stop|status}"
        exit 1
        ;;
esac

exit 0
