package com.epam.util.ssh.delegating;

import com.epam.util.common.CommonUtilException;
import com.epam.util.common.StringUtils;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class DelegatingExecSshChannel implements Closeable {
  private ChannelExec channelExec;

  public DelegatingExecSshChannel( Session session ) throws Exception {
    this.channelExec = (ChannelExec) session.openChannel( "exec" );
  }

  public String executeCommand( String command ) throws CommonUtilException {
    StringBuilder commandResult = new StringBuilder( StringUtils.EMPTY );
    channelExec.setCommand( command.trim() );

    try {
      channelExec.setInputStream( null );
//      channelExec.setErrStream( System.err );
      InputStream in = channelExec.getInputStream();
      channelExec.connect();
      byte[] tmp = new byte[ 10024 ];
      while ( true ) {
        while ( in.available() > 0 ) {
          int i = in.read( tmp, 0, 5024 );
          if ( i < 0 ) {
            break;
          }
          commandResult.append( new String( tmp, 0, i ) );
        }
        if ( channelExec.isClosed() ) {
          if ( in.available() > 0 ) {
            continue;
          }
          break;
        }
        try {
          Thread.sleep( 100 );
        } catch ( Exception ex ) {
          System.out.println( ex.getMessage() );
        }
      }

      return commandResult.toString();
    } catch ( JSchException | IOException ex ) {
      throw new CommonUtilException( ex );
    }
  }

  @Override public void close() throws IOException {
    if ( channelExec.isConnected() ) {
      channelExec.disconnect();
    }
  }
}
