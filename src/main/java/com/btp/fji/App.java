package com.btp.fji;

import com.btp.fji.service.HeatDissipatorService;
import com.btp.fji.util.PgmReader;
import java.io.IOException;
import java.util.Objects;

public class App {

    public static void main(String[] args) throws IOException {
        Integer iterations = null;
        Double minDelta = null;

        if (args.length != 4) {
            System.err.println("Invalid amount of arguments");
            System.exit(1);
        }

        String option = args[2];

        if (option.equals("-m")) {
            minDelta = Double.parseDouble(args[3]);
        } else if (option.equals("-i")) {
            iterations = Integer.parseInt(args[3]);
        } else {
            System.err.println("Valid end condition missing");
            System.exit(1);
        }

        double[][] heat = PgmReader.read(args[0]);
        double[][] cond = PgmReader.read(args[1]);

        HeatDissipatorService service = new HeatDissipatorService(heat, cond);

        if (Objects.nonNull(iterations)) {
            service.run(iterations, false);
        } else {
            service.run(minDelta, false);
        }
    }

}
