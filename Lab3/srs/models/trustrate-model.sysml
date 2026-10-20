package TrustRateModel {


    part def User {
        attribute userId : String;
        attribute email : String;
        attribute verificationLevel : Integer;
        attribute trustRating : Real;
    }

    part def AccountManagement {
        attribute supportsRegistration : Boolean;
        attribute supportsEmailVerification : Boolean;
        attribute supportsRoleManagement : Boolean;
    }

    part def DealManagement {
        attribute supportsDealStatuses : Boolean;
        attribute allowsReviewAfterCompletionOnly : Boolean;
    }

    part def ReviewManagement {
        attribute minimumScore : Integer;
        attribute maximumScore : Integer;
        attribute preventsDuplicateReviews : Boolean;
    }

    part def TrustPointsWallet {
        attribute balance : Real;
        attribute supportsTransactionHistory : Boolean;
        attribute supportsEscrow : Boolean;
    }

    part def VerificationService {
        attribute supportsEmailVerification : Boolean;
        attribute supportsPhoneVerification : Boolean;
        attribute supportsManualDocumentReview : Boolean;
    }

    part def AntiFraudAndModeration {
        attribute detectsSuspiciousActivity : Boolean;
        attribute requiresModeratorDecisionForSanctions : Boolean;
        attribute supportsAppeals : Boolean;
    }

    part def AuditLog {
        attribute recordsDealChanges : Boolean;
        attribute recordsReviewModeration : Boolean;
        attribute recordsTrustPointOperations : Boolean;
    }

    part def TrustRateDatabase {
        attribute storesUsers : Boolean;
        attribute storesDeals : Boolean;
        attribute storesReviews : Boolean;
        attribute storesTrustPointTransactions : Boolean;
        attribute storesModerationEvents : Boolean;
    }

    part def TrustRateApplication {
        part accounts : AccountManagement;
        part deals : DealManagement;
        part reviews : ReviewManagement;
        part wallet : TrustPointsWallet;
        part verification : VerificationService;
        part moderation : AntiFraudAndModeration;
        part audit : AuditLog;
    }

    part def TrustRateContext {
        part guest : User;
        part registeredUser : User;
        part moderator : User;
        part administrator : User;
        part application : TrustRateApplication;
        part database : TrustRateDatabase;
    }

    part trustRateSystem : TrustRateContext;
}
