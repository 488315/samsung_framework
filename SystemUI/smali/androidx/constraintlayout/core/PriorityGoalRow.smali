.class public final Landroidx/constraintlayout/core/PriorityGoalRow;
.super Landroidx/constraintlayout/core/ArrayRow;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final accessor:Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;

.field public arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

.field public numGoals:I

.field public sortArray:[Landroidx/constraintlayout/core/SolverVariable;


# direct methods
.method public constructor <init>(Landroidx/constraintlayout/core/Cache;)V
    .locals 1

    .line 1
    invoke-direct {p0, p1}, Landroidx/constraintlayout/core/ArrayRow;-><init>(Landroidx/constraintlayout/core/Cache;)V

    .line 2
    .line 3
    .line 4
    const/16 p1, 0x80

    .line 5
    .line 6
    new-array v0, p1, [Landroidx/constraintlayout/core/SolverVariable;

    .line 7
    .line 8
    iput-object v0, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 9
    .line 10
    new-array p1, p1, [Landroidx/constraintlayout/core/SolverVariable;

    .line 11
    .line 12
    iput-object p1, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->sortArray:[Landroidx/constraintlayout/core/SolverVariable;

    .line 13
    .line 14
    const/4 p1, 0x0

    .line 15
    iput p1, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 16
    .line 17
    new-instance p1, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;

    .line 18
    .line 19
    invoke-direct {p1, p0, p0}, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;-><init>(Landroidx/constraintlayout/core/PriorityGoalRow;Landroidx/constraintlayout/core/PriorityGoalRow;)V

    .line 20
    .line 21
    .line 22
    iput-object p1, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->accessor:Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;

    .line 23
    .line 24
    return-void
.end method


# virtual methods
.method public final addToGoal(Landroidx/constraintlayout/core/SolverVariable;)V
    .locals 5

    .line 1
    iget v0, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    add-int/2addr v0, v1

    .line 5
    iget-object v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 6
    .line 7
    array-length v3, v2

    .line 8
    if-le v0, v3, :cond_0

    .line 9
    .line 10
    array-length v0, v2

    .line 11
    mul-int/lit8 v0, v0, 0x2

    .line 12
    .line 13
    invoke-static {v2, v0}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object v0

    .line 17
    check-cast v0, [Landroidx/constraintlayout/core/SolverVariable;

    .line 18
    .line 19
    iput-object v0, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 20
    .line 21
    array-length v2, v0

    .line 22
    mul-int/lit8 v2, v2, 0x2

    .line 23
    .line 24
    invoke-static {v0, v2}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    .line 25
    .line 26
    .line 27
    move-result-object v0

    .line 28
    check-cast v0, [Landroidx/constraintlayout/core/SolverVariable;

    .line 29
    .line 30
    iput-object v0, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->sortArray:[Landroidx/constraintlayout/core/SolverVariable;

    .line 31
    .line 32
    :cond_0
    iget-object v0, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 33
    .line 34
    iget v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 35
    .line 36
    aput-object p1, v0, v2

    .line 37
    .line 38
    add-int/2addr v2, v1

    .line 39
    iput v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 40
    .line 41
    if-le v2, v1, :cond_2

    .line 42
    .line 43
    sub-int/2addr v2, v1

    .line 44
    aget-object v0, v0, v2

    .line 45
    .line 46
    iget v0, v0, Landroidx/constraintlayout/core/SolverVariable;->id:I

    .line 47
    .line 48
    iget v2, p1, Landroidx/constraintlayout/core/SolverVariable;->id:I

    .line 49
    .line 50
    if-le v0, v2, :cond_2

    .line 51
    .line 52
    const/4 v0, 0x0

    .line 53
    move v2, v0

    .line 54
    :goto_0
    iget v3, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 55
    .line 56
    if-ge v2, v3, :cond_1

    .line 57
    .line 58
    iget-object v3, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->sortArray:[Landroidx/constraintlayout/core/SolverVariable;

    .line 59
    .line 60
    iget-object v4, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 61
    .line 62
    aget-object v4, v4, v2

    .line 63
    .line 64
    aput-object v4, v3, v2

    .line 65
    .line 66
    add-int/lit8 v2, v2, 0x1

    .line 67
    .line 68
    goto :goto_0

    .line 69
    :cond_1
    iget-object v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->sortArray:[Landroidx/constraintlayout/core/SolverVariable;

    .line 70
    .line 71
    new-instance v4, Landroidx/constraintlayout/core/PriorityGoalRow$1;

    .line 72
    .line 73
    invoke-direct {v4, p0}, Landroidx/constraintlayout/core/PriorityGoalRow$1;-><init>(Landroidx/constraintlayout/core/PriorityGoalRow;)V

    .line 74
    .line 75
    .line 76
    invoke-static {v2, v0, v3, v4}, Ljava/util/Arrays;->sort([Ljava/lang/Object;IILjava/util/Comparator;)V

    .line 77
    .line 78
    .line 79
    :goto_1
    iget v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 80
    .line 81
    if-ge v0, v2, :cond_2

    .line 82
    .line 83
    iget-object v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 84
    .line 85
    iget-object v3, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->sortArray:[Landroidx/constraintlayout/core/SolverVariable;

    .line 86
    .line 87
    aget-object v3, v3, v0

    .line 88
    .line 89
    aput-object v3, v2, v0

    .line 90
    .line 91
    add-int/lit8 v0, v0, 0x1

    .line 92
    .line 93
    goto :goto_1

    .line 94
    :cond_2
    iput-boolean v1, p1, Landroidx/constraintlayout/core/SolverVariable;->inGoal:Z

    .line 95
    .line 96
    invoke-virtual {p1, p0}, Landroidx/constraintlayout/core/SolverVariable;->addToRow(Landroidx/constraintlayout/core/ArrayRow;)V

    .line 97
    .line 98
    .line 99
    return-void
.end method

.method public final getPivotCandidate([Z)Landroidx/constraintlayout/core/SolverVariable;
    .locals 11

    .line 1
    const/4 v0, -0x1

    .line 2
    const/4 v1, 0x0

    .line 3
    move v3, v0

    .line 4
    move v2, v1

    .line 5
    :goto_0
    iget v4, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 6
    .line 7
    if-ge v2, v4, :cond_8

    .line 8
    .line 9
    iget-object v4, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 10
    .line 11
    aget-object v5, v4, v2

    .line 12
    .line 13
    iget v6, v5, Landroidx/constraintlayout/core/SolverVariable;->id:I

    .line 14
    .line 15
    aget-boolean v6, p1, v6

    .line 16
    .line 17
    if-eqz v6, :cond_0

    .line 18
    .line 19
    goto :goto_7

    .line 20
    :cond_0
    iget-object v6, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->accessor:Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;

    .line 21
    .line 22
    iput-object v5, v6, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 23
    .line 24
    const/4 v5, 0x1

    .line 25
    const/16 v7, 0x8

    .line 26
    .line 27
    if-ne v3, v0, :cond_4

    .line 28
    .line 29
    :goto_1
    if-ltz v7, :cond_3

    .line 30
    .line 31
    iget-object v4, v6, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 32
    .line 33
    iget-object v4, v4, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 34
    .line 35
    aget v4, v4, v7

    .line 36
    .line 37
    const/4 v8, 0x0

    .line 38
    cmpl-float v9, v4, v8

    .line 39
    .line 40
    if-lez v9, :cond_1

    .line 41
    .line 42
    goto :goto_2

    .line 43
    :cond_1
    cmpg-float v4, v4, v8

    .line 44
    .line 45
    if-gez v4, :cond_2

    .line 46
    .line 47
    goto :goto_3

    .line 48
    :cond_2
    add-int/lit8 v7, v7, -0x1

    .line 49
    .line 50
    goto :goto_1

    .line 51
    :cond_3
    :goto_2
    move v5, v1

    .line 52
    :goto_3
    if-eqz v5, :cond_7

    .line 53
    .line 54
    goto :goto_6

    .line 55
    :cond_4
    aget-object v4, v4, v3

    .line 56
    .line 57
    :goto_4
    if-ltz v7, :cond_6

    .line 58
    .line 59
    iget-object v8, v4, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 60
    .line 61
    aget v8, v8, v7

    .line 62
    .line 63
    iget-object v9, v6, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 64
    .line 65
    iget-object v9, v9, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 66
    .line 67
    aget v9, v9, v7

    .line 68
    .line 69
    cmpl-float v10, v9, v8

    .line 70
    .line 71
    if-nez v10, :cond_5

    .line 72
    .line 73
    add-int/lit8 v7, v7, -0x1

    .line 74
    .line 75
    goto :goto_4

    .line 76
    :cond_5
    cmpg-float v4, v9, v8

    .line 77
    .line 78
    if-gez v4, :cond_6

    .line 79
    .line 80
    goto :goto_5

    .line 81
    :cond_6
    move v5, v1

    .line 82
    :goto_5
    if-eqz v5, :cond_7

    .line 83
    .line 84
    :goto_6
    move v3, v2

    .line 85
    :cond_7
    :goto_7
    add-int/lit8 v2, v2, 0x1

    .line 86
    .line 87
    goto :goto_0

    .line 88
    :cond_8
    if-ne v3, v0, :cond_9

    .line 89
    .line 90
    const/4 p0, 0x0

    .line 91
    return-object p0

    .line 92
    :cond_9
    iget-object p0, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 93
    .line 94
    aget-object p0, p0, v3

    .line 95
    .line 96
    return-object p0
.end method

.method public final isEmpty()Z
    .locals 0

    .line 1
    iget p0, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 2
    .line 3
    if-nez p0, :cond_0

    .line 4
    .line 5
    const/4 p0, 0x1

    .line 6
    goto :goto_0

    .line 7
    :cond_0
    const/4 p0, 0x0

    .line 8
    :goto_0
    return p0
.end method

.method public final removeGoal(Landroidx/constraintlayout/core/SolverVariable;)V
    .locals 5

    .line 1
    const/4 v0, 0x0

    .line 2
    move v1, v0

    .line 3
    :goto_0
    iget v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 4
    .line 5
    if-ge v1, v2, :cond_2

    .line 6
    .line 7
    iget-object v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 8
    .line 9
    aget-object v2, v2, v1

    .line 10
    .line 11
    if-ne v2, p1, :cond_1

    .line 12
    .line 13
    :goto_1
    iget v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 14
    .line 15
    add-int/lit8 v3, v2, -0x1

    .line 16
    .line 17
    if-ge v1, v3, :cond_0

    .line 18
    .line 19
    iget-object v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 20
    .line 21
    add-int/lit8 v3, v1, 0x1

    .line 22
    .line 23
    aget-object v4, v2, v3

    .line 24
    .line 25
    aput-object v4, v2, v1

    .line 26
    .line 27
    move v1, v3

    .line 28
    goto :goto_1

    .line 29
    :cond_0
    add-int/lit8 v2, v2, -0x1

    .line 30
    .line 31
    iput v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 32
    .line 33
    iput-boolean v0, p1, Landroidx/constraintlayout/core/SolverVariable;->inGoal:Z

    .line 34
    .line 35
    return-void

    .line 36
    :cond_1
    add-int/lit8 v1, v1, 0x1

    .line 37
    .line 38
    goto :goto_0

    .line 39
    :cond_2
    return-void
.end method

.method public final toString()Ljava/lang/String;
    .locals 4

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, " goal -> ("

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget v1, p0, Landroidx/constraintlayout/core/ArrayRow;->constantValue:F

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ") : "

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 19
    .line 20
    .line 21
    move-result-object v0

    .line 22
    const/4 v1, 0x0

    .line 23
    :goto_0
    iget v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->numGoals:I

    .line 24
    .line 25
    if-ge v1, v2, :cond_0

    .line 26
    .line 27
    iget-object v2, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->arrayGoals:[Landroidx/constraintlayout/core/SolverVariable;

    .line 28
    .line 29
    aget-object v2, v2, v1

    .line 30
    .line 31
    iget-object v3, p0, Landroidx/constraintlayout/core/PriorityGoalRow;->accessor:Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;

    .line 32
    .line 33
    iput-object v2, v3, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 34
    .line 35
    new-instance v2, Ljava/lang/StringBuilder;

    .line 36
    .line 37
    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    .line 38
    .line 39
    .line 40
    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 41
    .line 42
    .line 43
    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 44
    .line 45
    .line 46
    const-string v0, " "

    .line 47
    .line 48
    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 49
    .line 50
    .line 51
    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 52
    .line 53
    .line 54
    move-result-object v0

    .line 55
    add-int/lit8 v1, v1, 0x1

    .line 56
    .line 57
    goto :goto_0

    .line 58
    :cond_0
    return-object v0
.end method

.method public final updateFromRow(Landroidx/constraintlayout/core/LinearSystem;Landroidx/constraintlayout/core/ArrayRow;Z)V
    .locals 17

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    move-object/from16 v1, p2

    .line 4
    .line 5
    iget-object v2, v1, Landroidx/constraintlayout/core/ArrayRow;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 6
    .line 7
    if-nez v2, :cond_0

    .line 8
    .line 9
    return-void

    .line 10
    :cond_0
    iget-object v3, v1, Landroidx/constraintlayout/core/ArrayRow;->variables:Landroidx/constraintlayout/core/ArrayRow$ArrayRowVariables;

    .line 11
    .line 12
    invoke-interface {v3}, Landroidx/constraintlayout/core/ArrayRow$ArrayRowVariables;->getCurrentSize()I

    .line 13
    .line 14
    .line 15
    move-result v4

    .line 16
    const/4 v6, 0x0

    .line 17
    :goto_0
    if-ge v6, v4, :cond_9

    .line 18
    .line 19
    invoke-interface {v3, v6}, Landroidx/constraintlayout/core/ArrayRow$ArrayRowVariables;->getVariable(I)Landroidx/constraintlayout/core/SolverVariable;

    .line 20
    .line 21
    .line 22
    move-result-object v7

    .line 23
    invoke-interface {v3, v6}, Landroidx/constraintlayout/core/ArrayRow$ArrayRowVariables;->getVariableValue(I)F

    .line 24
    .line 25
    .line 26
    move-result v8

    .line 27
    iget-object v9, v0, Landroidx/constraintlayout/core/PriorityGoalRow;->accessor:Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;

    .line 28
    .line 29
    iput-object v7, v9, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 30
    .line 31
    iget-boolean v10, v7, Landroidx/constraintlayout/core/SolverVariable;->inGoal:Z

    .line 32
    .line 33
    const/4 v11, 0x1

    .line 34
    const v12, 0x38d1b717    # 1.0E-4f

    .line 35
    .line 36
    .line 37
    const/16 v13, 0x9

    .line 38
    .line 39
    const/4 v14, 0x0

    .line 40
    if-eqz v10, :cond_4

    .line 41
    .line 42
    const/4 v10, 0x0

    .line 43
    :goto_1
    if-ge v10, v13, :cond_2

    .line 44
    .line 45
    iget-object v15, v9, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 46
    .line 47
    iget-object v15, v15, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 48
    .line 49
    aget v16, v15, v10

    .line 50
    .line 51
    iget-object v5, v2, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 52
    .line 53
    aget v5, v5, v10

    .line 54
    .line 55
    mul-float/2addr v5, v8

    .line 56
    add-float v5, v5, v16

    .line 57
    .line 58
    aput v5, v15, v10

    .line 59
    .line 60
    invoke-static {v5}, Ljava/lang/Math;->abs(F)F

    .line 61
    .line 62
    .line 63
    move-result v5

    .line 64
    cmpg-float v5, v5, v12

    .line 65
    .line 66
    if-gez v5, :cond_1

    .line 67
    .line 68
    iget-object v5, v9, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 69
    .line 70
    iget-object v5, v5, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 71
    .line 72
    aput v14, v5, v10

    .line 73
    .line 74
    goto :goto_2

    .line 75
    :cond_1
    const/4 v11, 0x0

    .line 76
    :goto_2
    add-int/lit8 v10, v10, 0x1

    .line 77
    .line 78
    goto :goto_1

    .line 79
    :cond_2
    if-eqz v11, :cond_3

    .line 80
    .line 81
    iget-object v5, v9, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 82
    .line 83
    iget-object v9, v9, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->this$0:Landroidx/constraintlayout/core/PriorityGoalRow;

    .line 84
    .line 85
    invoke-virtual {v9, v5}, Landroidx/constraintlayout/core/PriorityGoalRow;->removeGoal(Landroidx/constraintlayout/core/SolverVariable;)V

    .line 86
    .line 87
    .line 88
    :cond_3
    const/4 v11, 0x0

    .line 89
    goto :goto_5

    .line 90
    :cond_4
    const/4 v5, 0x0

    .line 91
    :goto_3
    if-ge v5, v13, :cond_7

    .line 92
    .line 93
    iget-object v10, v2, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 94
    .line 95
    aget v10, v10, v5

    .line 96
    .line 97
    cmpl-float v15, v10, v14

    .line 98
    .line 99
    if-eqz v15, :cond_6

    .line 100
    .line 101
    mul-float/2addr v10, v8

    .line 102
    invoke-static {v10}, Ljava/lang/Math;->abs(F)F

    .line 103
    .line 104
    .line 105
    move-result v15

    .line 106
    cmpg-float v15, v15, v12

    .line 107
    .line 108
    if-gez v15, :cond_5

    .line 109
    .line 110
    move v10, v14

    .line 111
    :cond_5
    iget-object v15, v9, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 112
    .line 113
    iget-object v15, v15, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 114
    .line 115
    aput v10, v15, v5

    .line 116
    .line 117
    goto :goto_4

    .line 118
    :cond_6
    iget-object v10, v9, Landroidx/constraintlayout/core/PriorityGoalRow$GoalVariableAccessor;->variable:Landroidx/constraintlayout/core/SolverVariable;

    .line 119
    .line 120
    iget-object v10, v10, Landroidx/constraintlayout/core/SolverVariable;->goalStrengthVector:[F

    .line 121
    .line 122
    aput v14, v10, v5

    .line 123
    .line 124
    :goto_4
    add-int/lit8 v5, v5, 0x1

    .line 125
    .line 126
    goto :goto_3

    .line 127
    :cond_7
    :goto_5
    if-eqz v11, :cond_8

    .line 128
    .line 129
    invoke-virtual {v0, v7}, Landroidx/constraintlayout/core/PriorityGoalRow;->addToGoal(Landroidx/constraintlayout/core/SolverVariable;)V

    .line 130
    .line 131
    .line 132
    :cond_8
    iget v5, v0, Landroidx/constraintlayout/core/ArrayRow;->constantValue:F

    .line 133
    .line 134
    iget v7, v1, Landroidx/constraintlayout/core/ArrayRow;->constantValue:F

    .line 135
    .line 136
    mul-float/2addr v7, v8

    .line 137
    add-float/2addr v7, v5

    .line 138
    iput v7, v0, Landroidx/constraintlayout/core/ArrayRow;->constantValue:F

    .line 139
    .line 140
    add-int/lit8 v6, v6, 0x1

    .line 141
    .line 142
    goto :goto_0

    .line 143
    :cond_9
    invoke-virtual {v0, v2}, Landroidx/constraintlayout/core/PriorityGoalRow;->removeGoal(Landroidx/constraintlayout/core/SolverVariable;)V

    .line 144
    .line 145
    .line 146
    return-void
.end method
