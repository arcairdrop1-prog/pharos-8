import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WalletRiskVerifier {

    // Representation of a wallet or contract profile (Requirement 01)
    public static class WalletProfile {
        String address;
        int contractAgeDays;
        boolean isCodeVerified;
        boolean interactedWithFlaggedAddresses;
        boolean hasUnusualApprovals;
        boolean isFreshlyFunded;

        public WalletProfile(String address, int age, boolean verified, boolean flaggedInteractions, boolean unusualApprovals, boolean freshFunded) {
            this.address = address;
            this.contractAgeDays = age;
            this.isCodeVerified = verified;
            this.interactedWithFlaggedAddresses = flaggedInteractions;
            this.hasUnusualApprovals = unusualApprovals;
            this.isFreshlyFunded = freshFunded;
        }
    }

    // Risk Assessment result container (Requirements 02 & 03)
    public static class RiskAssessment {
        String riskLevel; // "LOW", "MEDIUM", "HIGH"
        List<String> flags;
        List<String> explanations;

        public RiskAssessment(String riskLevel, List<String> flags, List<String> explanations) {
            this.riskLevel = riskLevel;
            this.flags = flags;
            this.explanations = explanations;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Risk Level: %s\n", riskLevel));
            sb.append("Flags & Explanations:\n");
            for (int i = 0; i < flags.size(); i++) {
                sb.append(String.format(" - [%s]: %s\n", flags.get(i), explanations.get(i)));
            }
            return sb.toString();
        }
    }

    // Requirements 01, 02 & 03: Checks at least 4 signals, assigns risk level, and explains flags in plain language
    public RiskAssessment verifyWallet(WalletProfile wallet) {
        List<String> flags = new ArrayList<>();
        List<String> explanations = new ArrayList<>();
        int riskScore = 0;

        // Signal 1: Contract Age
        if (wallet.contractAgeDays < 30) {
            flags.add("NEW_CONTRACT");
            explanations.add("The contract is very young (" + wallet.contractAgeDays + " days old), which increases the likelihood of untested or malicious code.");
            riskScore += 2;
        } else {
            flags.add("MATURE_CONTRACT");
            explanations.add("The contract has been active for over 30 days, indicating stability.");
        }

        // Signal 2: Verified Code
        if (!wallet.isCodeVerified) {
            flags.add("UNVERIFIED_CODE");
            explanations.add("The source code is not verified on the block explorer, preventing security audits.");
            riskScore += 3;
        } else {
            flags.add("VERIFIED_CODE");
            explanations.add("The source code is publicly verified and transparent.");
        }

        // Signal 3: Interaction with Flagged Addresses
        if (wallet.interactedWithFlaggedAddresses) {
            flags.add("FLAGGED_INTERACTION");
            explanations.add("The address has previously interacted with known malicious or hacked entities.");
            riskScore += 3;
        } else {
            flags.add("CLEAN_INTERACTION");
            explanations.add("No prior interactions with known malicious addresses were detected.");
        }

        // Signal 4: Unusual Approvals & Freshly Funded Wallet
        if (wallet.hasUnusualApprovals) {
            flags.add("UNUSUAL_APPROVALS");
            explanations.add("Detects unlimited token approvals or suspicious permission patterns.");
            riskScore += 2;
        }
        if (wallet.isFreshlyFunded) {
            flags.add("FRESHLY_FUNDED");
            explanations.add("The wallet was recently funded with no prior transaction history, common in Sybil or burner wallets.");
            riskScore += 1;
        }

        // Determine Risk Level (Requirement 02)
        String riskLevel;
        if (riskScore >= 5) {
            riskLevel = "HIGH";
        } else if (riskScore >= 2) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        return new RiskAssessment(riskLevel, flags, explanations);
    }

    // Requirement 04: Demonstrated with at least 1 clean example and 1 risky example
    public static void main(String[] args) {
        WalletRiskVerifier verifier = new WalletRiskVerifier();

        System.out.println("=== WALLET RISK VERIFIER AGENT DEMONSTRATION ===");

        // Example 1: Clean / Safe Target
        System.out.println("\n--- Example 1: Clean / Safe Target ---");
        WalletProfile cleanWallet = new WalletProfile(
            "0x1234...SafeContract", 365, true, false, false, false
        );
        RiskAssessment cleanResult = verifier.verifyWallet(cleanWallet);
        System.out.println(cleanResult);

        // Example 2: Risky / Suspicious Target
        System.out.println("\n--- Example 2: Risky / Suspicious Target ---");
        WalletProfile riskyWallet = new WalletProfile(
            "0xDead...BadContract", 5, false, true, true, true
        );
        RiskAssessment riskyResult = verifier.verifyWallet(riskyWallet);
        System.out.println(riskyResult);
    }
}
