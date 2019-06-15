package com.btp.fji;

import com.btp.fji.service.HeatDissipatorService;
import com.btp.fji.util.PgmReader;
import java.io.IOException;
import java.util.Objects;

public class App {

    public static void main(String[] args) throws Exception {
        // Default config
        String fileDir = null;
        int nrExecutorsPerNode = 1;
        double minDifference = 10;
        int maxIterations = Integer.MAX_VALUE;
        int height = 10;
        int width = 10;

        // overwrite defaults with input arguments
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-f":
                    fileDir = args[i + 1];
                    break;
                case "-e":
                    nrExecutorsPerNode = Integer.parseInt(args[i + 1]);
                    break;
                case "-d":
                    minDifference = Double.parseDouble(args[i + 1]);
                    break;
                case "-m":
                    maxIterations = Integer.parseInt(args[i + 1]);
                    break;
                case "-h":
                    height = Integer.parseInt(args[i + 1]);
                    break;
                case "-w":
                    width = Integer.parseInt(args[i + 1]);
                    break;
                default:
                    throw new Error("Usage: java HeatDissipatorApp "
                        + " -f fileDir "
                        + "[ -e <nrOfExecutors> ]"
                        + "[ -d <minDelta> ]"
                        + "[ -m <maxIteration> ]"
                        + "[ -h <height> ]"
                        + "[ -w <width> ]");
            }
        }

        double[][] heat = PgmReader.getTempValues(fileDir, height, width);
        double[][] cond = PgmReader.getCondValues(fileDir, height, width);

        HeatDissipatorService service = new HeatDissipatorService(heat, cond);
        service.run(minDifference, false);
    }

}
