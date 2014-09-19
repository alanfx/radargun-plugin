package org.jenkinsci.plugins.radargun;

import hudson.Launcher.ProcStarter;
import hudson.Proc;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NodeRunner implements Runnable {
    
    private static Logger LOGGER = Logger.getLogger(NodeRunner.class.getName());
    
    private final ProcStarter procStarter;
    private final RadarGunNodeAction nodeAction;
    private CountDownLatch latch;
    
    public NodeRunner(ProcStarter procStarter, RadarGunNodeAction nodeAction) {
        this.procStarter = procStarter;
        this.nodeAction = nodeAction;
    }

    public void setLatch(final CountDownLatch latch) {
        this.latch = latch;
    }
    
    @Override
    public void run() {
        LOGGER.log(Level.FINER, String.format("Staring runner %s", nodeAction.getDisplayName()));
        nodeAction.setInProgress(true);
        
        try {
            Proc proc = procStarter.start();
            int retCode = proc.join();
            LOGGER.log(Level.FINER, String.format("Runner %s finished with exit code %3d", nodeAction.getDisplayName(), retCode));
        } catch(IOException e) {
            LOGGER.log(Level.INFO, String.format("Node runner %s failed", nodeAction.getDisplayName()), e);
        } catch(InterruptedException e) {
            LOGGER.log(Level.INFO, String.format("Node runner %s failed", nodeAction.getDisplayName()), e);
        }
        
        nodeAction.setInProgress(false);
        latch.countDown();
    }

}
