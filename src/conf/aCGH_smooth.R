#library(aCGH)
#setwd ("C:/test/data")
#datadir <- "C:/test/data"

#clones_info <- read.table(file=file.path(datadir, "T_GPR_R_aCGH_cloneinfo_NoDups.csv"),
#                          header = T, sep=",", quote = "", comment.char = "")

#log2_ratios <- read.table(file=file.path(datadir, "T_GPR_R_aCGH_log2ratios_NoDups.csv"),
#                          header = T, sep=",", quote = "", comment.char = "")


# delete the first column
log2_ratios = log2_ratios[,-1]

# cbind, Combine R Objects by Rows or Columns
log2_ratios=cbind(log2_ratios, log2_ratios)


aCGH_obj <- create.aCGH(log2_ratios, clones_info)

# Screens chromosome number > 24 along with some QC measures. 
aCGH_obj_imp <- aCGH.process(aCGH_obj, chrom.remove.threshold = 23, prop.missing=0.25, 
                             sample.quality.threshold=0.4, unmapScreen = TRUE, dupRemove = FALSE)

# Impute missing intensities using LOWESS method. 
#log2.ratios.imputed(aCGH_obj) <- impute.lowess(aCGH_obj_imp, maxChrom=24) 
## Imputed data exist

hmm(aCGH_obj) <-find.hmm.states(aCGH_obj_imp, aic=TRUE, delta=1.5)

hmm.merged(aCGH_obj) <- mergeHmmStates(aCGH_obj, model.use=1, minDiff=0.25)

sd.samples(aCGH_obj) <-computeSD.Samples(aCGH_obj)

#genomic.events(aCGH_obj) <-find.genomic.events(aCGH_obj)


# pdf("C:/test/Example_GenePix_HMM_States.pdf")
#  plotHmmStates(aCGH_obj, sample.ind=1, chr=1:23)
# dev.off()

# output to file
lst <- hmm(aCGH_obj)
#capture.output(lst[[1]][1], file = "C:/test/hmmAIC.txt", append = FALSE)
#capture.output(lst[[1]][2], file = "C:/test/hmmBIC.txt", append = FALSE)
#capture.output(lst[[1]][3], file = "C:/test/hmmBIC1.5.txt", append = FALSE)

# making data frame
df <- data.frame(hmm(aCGH_obj)[[1]][1])
# df[1] = chromosome
# df[2] = kb
# df[3] = state
# df[4] = smoothed value
# df[8] = observed value

result <- cbind(chromosome=df[1], kb=df[2], state=df[3], smoothed=df[4], observed=df[8])
#capture.output(df, file = "C:/test/dataframe.txt", append = FALSE)
capture.output(result, file = "C:/test/result.txt", append = FALSE)

